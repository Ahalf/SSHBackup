import paramiko
import os
import glob
import json
from syncConfig import Config
import pickle
import stat
import getpass
import base64

class Controller():

	def __init__(self):
		self.CONFIGDIR = '/Users/adamhalfaker/Documents/bin/csSync/configs/'
		self.BACKUPDIR = '/Users/adamhalfaker/Documents/bin/csSync/backups/'

	def __connect(self,host, user, password):
		client = paramiko.SSHClient()
		client.load_system_host_keys()
		client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		client.connect(host, username=user, password=password)
		return client

	def getConfigDir(self):
		return self.CONFIGDIR

	def setConfigDir(self, configDir):
		self.CONFIGDIR = configDir

	def setConfig(self):
		name = input("Enter config name: ")
		host = input("Enter host name: ")
		user = input("Enter username: ")

		password = base64.b64encode(str.encode(getpass.getpass("Enter password: ")))
		path = input("Enter path to file or directory: ")
		config = Config(name, host, user, password, path)
		return config
		
	def recurseFolders(self, sftp, remoteDir, file):
		tempRemoteDir = remoteDir + '/' + file.rsplit('/',1)[-1]
		sftp.mkdir(tempRemoteDir)
		sftp.chdir(tempRemoteDir)
		self.sftp_put_recursive(file, tempRemoteDir, sftp)

	def putFiles(self, remoteDir, file, sftp):
		remoteFile = remoteDir + '/' + file.rsplit('/',1)[-1]
		print("Putting " + file + " at " + remoteFile)
		sftp.put(file, remoteFile)

	def getFiles(self, remotePath, localPath, sftp):
		sftp.get(remotePath, localPath)
		print("Getting " + remotePath)

	def getRemoteBackup(self, configName):
		config = self.fetchConfig(configName)
		client = self.__connect(config.getHost(), config.getUser(), config.getPass())
		remoteDir = configName + '.backup'
		localBackup = self.BACKUPDIR + remoteDir

		stdin, stdout, stderr = client.exec_command("pwd")
		remoteBackup = stdout.read().splitlines()[0].decode('utf-8') + '/' + remoteDir
		
		sftp = self.startSFTPClient(client, remoteBackup)
		self.sftp_get_recursive(remoteBackup, localBackup, sftp)

	def sftp_get_recursive(self, path, dest, sftp):
		item_list = sftp.listdir(path)
		dest = str(dest)
		try:
			if not os.path.isdir(dest):
				os.makedirs(dest)
				os.chdir(dest)

			for item in item_list:
				item = str(item)
				remoteFile = path + '/' + item
				localDest = dest + '/' + item
				itemAttr = sftp.stat(remoteFile)
				if stat.S_ISDIR(itemAttr.st_mode):
					self.sftp_get_recursive(remoteFile, localDest, sftp)
				else:
					self.getFiles(remoteFile, localDest, sftp)
		except FileNotFoundError:
			print("Error at: " + item)


	def sftp_put_recursive(self, path, remoteDir, sftp):
		if(os.path.isdir(path)):
				path += '/*'
				for file in glob.iglob(path, recursive=True):
					if(os.path.isdir(file)):
						self.recurseFolders(sftp, remoteDir, file)
					else:
						self.putFiles(remoteDir, file, sftp)
						
		elif(os.path.isfile(path)):
			self.putFiles(remoteDir, file, sftp)
					
	def updateConfig(self, configName, field, update):
		config = self.fetchConfig(configName)
		if(field == 'host'):
			config.setHost(update)
		elif(field == 'password'):
			config.setPassword(update)
		elif(field == 'name'):
			config.setName(update)
		elif(field == 'user'):
			config.setUser(update)
		elif(field == 'path'):
			config.setPath(update)
		pickleFile = os.path.join(self.CONFIGDIR, config.getName() + '.pkl')
		with open(pickleFile, 'wb') as output:
			pickle.dump(config, output, pickle.HIGHEST_PROTOCOL)
		print("\n%s updated for %s" % (field, configName))

	def setupBackupFolder(self, client, config):
		client.exec_command("cd")
		client.exec_command("mkdir %s.backup" % config.getName())
		folderName = config.getName() + '.backup'
		stdin, stdout, stderr = client.exec_command("pwd")
		remoteDir = stdout.read().splitlines()[0].decode('utf-8') + '/' + folderName
		print(remoteDir)
		return remoteDir

	def deleteBackup(self,configName, client=False):
		config = self.fetchConfig(configName)
		if(not client):
			client = self.__connect(config.getHost(), config.getUser(), config.getPass())
		backupFolder = configName + '.backup'
		client.exec_command('rm -R %s' % backupFolder)

	def startSFTPClient(self, client, remoteDir):
		transport = client.get_transport()
		sftp = paramiko.SFTPClient.from_transport(transport)
		sftp.chdir(remoteDir)
		return sftp

	def createConfig(self):
		newConfig = self.setConfig()
		if not os.path.exists(self.CONFIGDIR):
			os.makedirs(self.CONFIGDIR)
		pickleFile = os.path.join(self.CONFIGDIR, newConfig.getName() + '.pkl')
		with open(pickleFile, 'wb') as output:
			pickle.dump(newConfig, output, pickle.HIGHEST_PROTOCOL)
		return newConfig

	def handleNoConfig(self):
		print("Config does not exist. Create config now?")
		option = input("Yes/No: ")
		if option.lower() == 'yes':
			config = self.createConfig()
			print("Config created!")
			return config

	def fetchConfig(self, configName):
		if configName == '':
			config = self.createConfig()
		else:
			try:
				pickleFile = os.path.join(self.CONFIGDIR, configName + '.pkl')
				if(os.path.exists(pickleFile)):
					with open(pickleFile, 'rb') as file:
						config = pickle.load(file)
				else:
					config = self.handleNoConfig()

			except KeyError:
				config = self.handleNoConfig()
		return config
		
	def deleteConfig(self,configName):
		os.remove(self.CONFIGDIR  + configName + '.pkl')
		print("%s deleted." % configName)

	def sync(self, configName=''):
		try:
			config = self.fetchConfig(configName)
			path = config.getPath() + ''
			client = self.__connect(config.getHost(), config.getUser(), config.getPass())
			self.deleteBackup(config.getName(), client)
			remoteDir = self.setupBackupFolder(client, config)
			sftp = self.startSFTPClient(client, remoteDir)
			self.sftp_put_recursive(path, remoteDir, sftp)
			print("\nSuccessfully updated %s" % remoteDir)
			print("\nFiles synched: %s\n" % path)
			
			sftp.close()
			client.close()
		except AttributeError:
			print("Config not created. Returning home.")

	def recurseFoldersForDisplay(self, path):
		if(os.path.isdir(path)):
				path += '/*'
				for file in glob.iglob(path, recursive=True):
					if(os.path.isdir(file)):
						self.recurseFoldersForDisplay(file)
					else:
						print(file)

	def displayDirContents(self, configName):
		config = self.fetchConfig(configName)
		path = config.getPath() + ''
		self.recurseFoldersForDisplay(path)
		




	