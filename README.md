# Movie lol android app

![Movie lol](https://cdn-images-1.medium.com/max/800/1*W72yT0lX6a3ogzuhApNqlQ.jpeg)

## What's this?
A movie information and comment android app.

The features in this app is quite simple, just show movie list in front page, and show the detail information, photos, trailers, and comments of movie when clicking one of the movie items in list.

![Functional Map](https://cdn-images-1.medium.com/max/800/1*XTktr2JTF4t4LuK4kbmkMg.png)

![UI Flow](https://cdn-images-1.medium.com/max/800/1*yd5smC-QqI3FG8FeCQDNRQ.jpeg)

The full introduction can check out [Part1](https://medium.com/@enginebai/movie-app-side-project-part-1-4e40a86420#.d83l2mks9), [Part2](https://medium.com/@enginebai/movie-app-side-project-part-2-512cdad5680a#.xhdo8rhoi).

## Components

**This project is finished before Google released the new Android Design Support Library, so there are some components can be replaced by new material design widgets.**


### Movie list page (front page)
![Front Page](https://cdn-images-1.medium.com/max/800/1*18ngBL1zK5LF7-zazlRwYA.png)

* **android.support.v7.widget.Toolbar** and [MaterialTab](https://github.com/neokree/MaterialTabs) on the top.
* **android.support.v7.widget.CardView** for each movie in the list.
* [FloatingActionButton](https://github.com/futuresimple/android-floating-action-button) to sort the movies.
* [SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView) to show move list.


### Movie detail page
![Movie detail translation](https://cdn-images-1.medium.com/max/800/1*gfSNw3TJTHGW8_rAtpXkwQ.gif)

* [ObservableScrollView](https://github.com/ksoichiro/Android-ObservableScrollView) for the whole page to interact with toolbar when scrolling the page.
* [Material](https://github.com/rey5137/material) which provides some material design components to pre-Lolipop Android.

### Photo list page
![https://cdn-images-1.medium.com/max/800/1*Hea4nFI47AGI4_TeQAc46w.png](https://cdn-images-1.medium.com/max/800/1*Hea4nFI47AGI4_TeQAc46w.png)

* [ListBuddiesLayout](https://github.com/jpardogo/ListBuddies) for photo list.

## Demo 
[Demo Video](https://youtu.be/RyO7dG2KpiQ)

## License

	The MIT License (MIT)

	Copyright © 2015 Engine Bai.

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.
