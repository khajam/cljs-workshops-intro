(ns ^:figwheel-always intro.core
    (:require[om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn a-component [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
               "a component"))))

(om/root
  (fn [data owner]
    (reify om/IRender
      (render [_]
        (dom/div nil
          (dom/h1 nil (:text data))
          (om/build a-component data)))))
  app-state
  {:target (. js/document (getElementById "app"))})


