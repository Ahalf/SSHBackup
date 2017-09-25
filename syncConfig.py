import base64
class Config():

	def __init__(self, name, host, user, password, path):
		self.name = name
		self.host = host
		self.user = user
		self.password = password
		self.path = path
		
	def setPath(self, path):
		self.path = path
	def setName(self, name):
		self.name = name
	def setHost(self, host):
		self.host = host
	def setUser(self, user):
		self.user = user
	def setPass(self, password):
		self.password = password

	def getName(self):
		return self.name
	def getHost(self):
		return self.host
	def getUser(self):
		return self.user
	def getPass(self):
		return base64.b64decode(self.password)
	def getPath(self):
		return self.path