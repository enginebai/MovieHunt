## File Structures
* `/base` module:
	* Responsibility: define the base, common and utilities classes.
	* It can be updated by merging base repository, and will not affect the project modules.
* `/app` module:
	* Responsibility: define all the features in your app.
	* Define `applicationId` as app package name.
	* Include all resources that app used. (including strings, colors, dimensions, drawables.)

## Dependencies
* `/app` include `/base` serves as common library that the whole project used.

## Don't do
Don't add anything to the module other than `/base`, since other modules will change the package to feature, and it will break the merge.

