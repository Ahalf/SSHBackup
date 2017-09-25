from syncController import Controller
from syncConfig import Config
import glob, os, sys

def showMenu():
	print("				Welcome to CSSync				")
	print("						Menu					")
	print("				1. Start synching				")
	print("				2. Get remote backup			")
	print("				3. Create config 				")
	print("				4. Search configs				")
	print("				5. Show all configs				")
	print("				6. Update config 				")
	print("				7. Delete config 				")
	print("				8. Delete backup				")
	print("				9. List dir contents			")
	print("				Exit. Exit						")

def printConfigStats(config):
	print("\nConfig stats:")
	print("Name: %s" % config.getName())
	print("Host: %s" % config.getHost())
	print("User: %s" % config.getUser())
	print("Password: *********")
	print("Path: %s" % config.getPath())

def sync():
	client = Controller()
	print('\nSync')
	print('--------------------------')
	configName = input("Enter config name: ")
	client.sync(configName)

def createConfig():
	client = Controller()
	client.createConfig()

def searchConfig():
	client = Controller()
	print('\nSearch')
	print('---------------------------')
	configName = input("Enter config name: ")
	configDir = client.getConfigDir()
	os.chdir(configDir)
	configDir += '/*'
	for file in glob.glob(configDir):
		concName = file.split('.')[0].rsplit('/',1)[-1]
		if (configName == concName):
			config = client.fetchConfig(configName)
			print(config)
			printConfigStats(config)

def updateConfig():
	client = Controller()
	print('\nUpdate Config')
	print('-------------------------')
	configName = input("Config name: ")
	field = input("What would you like to update? (Config name, host, user, password, path): ")
	update = input("Enter new value: ")
	client.updateConfig(configName, field, update)

def deleteConfig():
	client = Controller()
	print('\nDelete Config')
	print('---------------------------')
	configName = input("Enter config name: ")
	client.deleteConfig(configName)
	    	
def listConfigs():
	client = Controller()
	print('\nConfigs')
	print('--------------------------')
	configDir = client.getConfigDir()
	os.chdir(configDir)
	configDir += '/*'
	for file in glob.glob(configDir):
	    print(file.rsplit('/',1)[-1])

def deleteBackup():
	client = Controller()
	print("Delete Backup")
	print("----------------------------")
	configName = input("Enter config name: ")
	client.deleteBackup(configName)
	print('\n' + configName + '.backup deleted.')

def listFilesInConfig():
	client = Controller()
	print("Config Files")
	print('----------------------------')
	configName = input("Enter config name: ")
	client.displayDirContents(configName)

def getRemoteBackup():
	client = Controller()
	print("Get Remote backup")
	print('----------------------------')
	configName = input("Enter config name: ")
	client.getRemoteBackup(configName)


def exitcsSync():
	sys.exit(0)

options = {'1' : sync,
		   '2' : getRemoteBackup,
           '3' : createConfig,
           '4' : searchConfig,
           '5' : listConfigs,
           '6' : updateConfig,
           '7' : deleteConfig,
           '8' : deleteBackup,
           '9' : listFilesInConfig,
           'exit' : exitcsSync
}
