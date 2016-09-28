# Pre-work - Simple Todo

Simple Todo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Kuei-Jung Hu

Time spent: 21 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Prevent user from adding empty item titles when clicking on add/save in the AddItemActivity/EditItemActivity
* [x] Add support for sorting items by title, priority, due date or status and persist the sorting option into SQLite 
* [x] Add an alert dialog to confirm delete
* [x] Add splash screen and design the launcher icon (thanks to the cat Ralph)
* [x] Add localization for Traditional Chinese (Taiwan)
* [x] Add a refresh button in the EditItemActivity
* [x] Add support for selecting the status of each todo item (and display in listview item)

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/KgHNHhK.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Localization for Traditional Chinese (Taiwan)

<img src='http://i.imgur.com/OFRxxHO.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

* Todo: When the screen is rotated, the activity is killed and restarted. I'm currently trying to avoid that
* Todo: Need people to use the app and provide feedback

## License

    Copyright 2016 Kuei-Jung Hu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
