# WHAT?
The task is to create an app which loads navigation entries from an REST API `navigation` and populates them in the [navigation drawer](https://developer.android.com/training/implementing-navigation/nav-drawer.html). The structure of the response of the REST-API looks like the following

```javascript
{
	"navigationEntries": [{
		"type": "section",
		"label": "Sortiment",
		"children": [{
			"type": "node",
			"label": "Alter",
			"children": [{
				"type": "node",
				"label": "Baby & Kleinkind",
				"children": [{
					"type": "link",
					"label": "0-6 Monate",
					"url": "http:\/\/www.mytoys.de\/0-6-months\/"
				}, {
					"type": "link",
					"label": "7-12 Monate",
					"url": "http:\/\/www.mytoys.de\/7-12-months\/"
				}]
			}]
		}]
	}]
}
```

# Considerations before and during development
## Architecture
The uses is build with use of the [MVVM-architecture](http://media.hv.se/kurser/informatik-ail/gamla-arbeten-vri400/mvvm-model-view-viewmodel/)
This architecture consists of three parts
- View: Shows the data and informs the view model about user interaction
- ViewModel: Handles user interactions, loads data, exposes streams of events to which Views can bind.
- Model: The datasource

One of the main goals and benefits of the usage of this architecture is to not use any Android-Dependencies inside the viewmodel. This leads to the fact that unit testing becomes quite easy. Furthermore Google is supporting this architecture quite heavily by providing specific libraries for this [here](https://developer.android.com/topic/libraries/architecture/adding-components.html)

## Loading and caching data
Another approach which was considered to use ist the [repository-pattern](https://msdn.microsoft.com/en-us/library/ff649690.aspx). The repository pattern is a layer which decides from where to load the data this might be an webservice, a local database or a cache. Since one the requirements is to load data from a webservice it might make sense to store the entries in a database so the boot-time of the app is much faster next time. Furthermore the viewmodel has to communicate to just one interface for loading the navigation entries.

## Store the state the navigation
Currently the state of the current level is stored in a `stack` is stored inside the MainAcitivityViewModel.java. An navigation-entry will be pushed on the stack as soon a user clicks on an navigation entry. When the user clicks the back/ up button the latest element will be removed and the underlying element becomes the current element. There are also other approaches e.g. the whole structure could be a tree which means every navigation entry knows it's parent. This increases the complexity of the parser for the response because current element needs to be passed to it's children. 


