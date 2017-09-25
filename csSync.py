
import syncView

option = ''
while option != 'exit':
	syncView.showMenu()
	option = input('Enter choice: ')
	syncView.options[option.lower()]()


