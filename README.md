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
Furthermore a navigation entry has a type which is `section`, `node` or `link`. There is a limitation that a user is not able to click on a section.

# Considerations before and during development
## Architecture
The app is build with use of the [MVVM-architecture](http://media.hv.se/kurser/informatik-ail/gamla-arbeten-vri400/mvvm-model-view-viewmodel/)
This architecture consists of three parts
- View: Shows the data and informs the viewmodel about user interaction
- ViewModel: Handles user interactions, loads data, exposes streams of events to which Views can bind.
- Model: The datasource

One of the main goals and benefits of the usage of this architecture is to seperate pain view elements from the logic or handling of events. e.g. Button clicks. For Android development this means the viewmodel should not include any Android dependencies. This leads to the fact that unit testing becomes quite easy. Furthermore Google is supporting this architecture quite heavily by providing specific libraries for this [here](https://developer.android.com/topic/libraries/architecture/adding-components.html)

## Loading and caching data
As mentioned in the WHAT section navigation entries should be fetched from a webservice. The current state of the art approach is to use the [repository-pattern](https://msdn.microsoft.com/en-us/library/ff649690.aspx). The repository pattern is a layer which decides from where to load the data. This might be an webservice, a local database or a cache. Using this approach the viewmodel has only to communicate with one interface to retrieve the navigation entries. This fact also supports the single responsibility approach. 

## Store the state the navigation
Currently the state of the current level is stored in a `stack` is stored inside the MainAcitivityViewModel.java. An navigation-entry will be pushed on the stack as soon a user clicks on an navigation entry. When the user clicks the back/ up button the latest element will be removed and the underlying element becomes the current element. It is debatable if the stack should be part of the viewmodel itself or should be a single class/ object. 
There are also other approaches e.g. the whole structure could be a tree which means every navigation entry knows its parent. This increases the complexity of the parser for the response because the current element needs to be passed to his children. 


