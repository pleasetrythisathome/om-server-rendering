# Om Server Rendering

A stripped down example of [omelette](https://github.com/DomKM/omelette). Omelette's an awesome application and a great example of a number of other best practices, but I wanted a minimal test case for Om server rendering via [Nashorn](http://openjdk.java.net/projects/nashorn/). Here it is.

## Running

clone the repo

```lein run```

Nashorn is somewhat slow to warm up, so don't be alarmed when initial load takes 5+ seconds. Once warmed up, page load is very fast.

if you want to play with the code, it's important to be aware that the js file is read at compile time, not runtime, so you will need to re-evaluate the routes and restart the server to pick up changes. I'm sure there are ways around this, but that's for another project.

## Disclaimer

This is meant as an example and learning tool! Thoughts and pull requests are welcome.

## License

Copyright © 2014 Dylan Butman

Distributed under the Eclipse Public License.
