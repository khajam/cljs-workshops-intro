(ns ^:figwheel-always intro.core
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]
              [clojure.string :refer [join]]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; utils
(def l #(.log js/console (join " " %&)))

(defn with-init [f a]
  (fn [b]
    (f a b)))

(defn normalize [min max]
  (fn [x]
    (* (/ 1 (- max min)) x)))

((normalize 0 (* 2 80)) 160)
;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn sun-view [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "sun-view")
    om/IInitState
    (init-state [_]
      (let [radius 80]
        {:radius       radius
         :normalize-fn (normalize 0 (* 2 radius))
         :r            200
         :g            0
         :b            0
         :rel-x-fn     identity}))
    om/IRenderState
    (render-state [_ {:keys [radius normalize-fn r g b rel-x-fn]}]
      (dom/svg #js {:height 200 :width 200}
               (dom/circle #js {:r            radius :cx 80 :cy 80
                                :fill         (str "rgb(" (join "," [r g b]) ")")
                                :onMouseEnter #(let [x (.-clientX %)]
                                                (om/set-state! owner :rel-x-fn (with-init (comp Math/abs -) x)))
                                :onMouseMove  #(let [x (.-clientX %)]
                                                (l "x:" x)
                                                (l "rel-x:" (rel-x-fn x))
                                                (l "nomrd" (Math/round (* 255 (normalize-fn (rel-x-fn x)))))
                                                (om/set-state! owner :r (Math/round (* 255 (normalize-fn (rel-x-fn x))))))})))))

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
                 (om/build sun-view data)
                 (om/build sun-view data)
                 (om/build sun-view data)
                 (dom/h1 nil (:text data))))))
  app-state
  {:target (. js/document (getElementById "app"))})


