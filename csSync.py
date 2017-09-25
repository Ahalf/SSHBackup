
import syncView

option = ''
while option != 'exit':
	syncView.showMenu()
	option = input('Enter choice: ')
	option.lower()
	syncView.options[option]()


