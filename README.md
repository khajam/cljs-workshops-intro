### Initial Setup
We use Leiningen to manage dependencies. Make sure you [have it installed](http://leiningen.org/#install).
We use [figwheel](https://github.com/bhauman/lein-figwheel) to load up a server & reload our files.

Using Leiningen, we install a figwheel template, also making use of the option to add Om for later use.
```
lein new figwheel cljs-workshops-intro -- --om
```

### Development
`cd` into `cljs-workshops-intro` & spin up figwheel
```
lein figwheel
```
#### REPL Intro
At this point figwheel should have compiled & started a server serving our files.
Go to [http://localhost:3449/](http://localhost:3449/), where you should see a plain old "Hello world!"
**Once you visited our local server** figwheel will also return a ClojureScript REPL in your console, you should see something like
```
Prompt will show when figwheel connects to your application
To quit, type: :cljs/quit
ClojureScript:cljs.user>
```
#### Interop basics:

In JS:
```javascript
document.body.setAttribute("style", "background: cadetblue")
```
In CLJS:
```
(-> js/document .-body (.setAttribute "style" "background: orange"))
```

* `->` thread-first-macro
  * Inserts x as the second item in the first form, making a list of it if it is not a list already. If there are more forms, inserts the first form as the second item in second form, etc.
  * could also be written as: `(.setAttribute (.-body js/document) "style" "background: orange")`
* `.-body` is a property of `js/document`, you get properties by calling them on objects using `.-` as a prefix
* `.setAttribute` is a function, so you have to invoke it by wrapping it in parens
  * `"style"` & `"background: orange"` are argument strings, same as in JS

###### nREPL (optional)
If you want to evaluate forms from you editor (emacs, vim, lightable, intellij) you will want to connect
 to a remote repl. Figwheel provides a port to connect to (uncomment `:nrepl-port 7888` in your project.clj).
 In your editor, connect to localhost:7888. You will first get a Clojure repl, to make it a CLJS repl

```clojure
(use 'figwheel-sidecar.repl-api)
(cljs-repl)
```
