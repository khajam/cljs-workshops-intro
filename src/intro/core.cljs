(ns ^:figwheel-always intro.core
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]
              [clojure.string :refer [join]]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn sun-view [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "sun-view")
    om/IInitState
    (init-state [_]
      {:r 200
       :g 0
       :b 0})
    om/IRenderState
    (render-state [_ {:keys [r g b]}]
      (dom/svg #js {:height 200 :width 200}
               (dom/circle #js {:r           80 :cx 80 :cy 80
                                :fill        (str "rgb(" (join "," [r g b]) ")")
                                :onMouseMove #(.log js/console (.-clientX %))})))))

(om/root
  (fn [data owner]
    (reify
      om/IDisplayName
      (display-name [_] "ROOT")
      om/IRender
      (render [_]
        (dom/div #js {:style #js {:height         "400px"
                                  :display        "flex"
                                  :justifyContent "space-around"
                                  :alignItems     "center"}}
          (dom/h1 nil (:text data))
          (om/build sun-view data)))))
  app-state
  {:target (. js/document (getElementById "app"))})


