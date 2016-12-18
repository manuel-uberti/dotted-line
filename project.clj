(defproject dotted-line "0.1.0-SNAPSHOT"

  :description "dotted-line: Boccaperta Management System"
  :url "https://github.com/manuel-uberti/dotted-line"

  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [selmer "1.10.2"]
                 [markdown-clj "0.9.91"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "1.0.0"]
                 [org.webjars/bootstrap "4.0.0-alpha.5"]
                 [org.webjars/font-awesome "4.7.0"]
                 [org.webjars.bower/tether "1.3.7"]
                 [org.webjars/jquery "3.1.1-1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.5.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.0"]
                 [mount "0.1.10"]
                 [cprop "0.1.7"]
                 [org.clojure/tools.cli "0.3.5"]
                 [luminus-nrepl "0.1.4"]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [luminus-immutant "0.2.2"]
                 [buddy "1.2.0"]
                 [luminus-migrations "0.1.0"]
                 [conman "0.4.9"]
                 [org.postgresql/postgresql "9.4-1206-jdbc4"]
                 [luminus-log4j "0.1.3"]
                 [medley "0.8.4"]
                 [acyclic/squiggly-clojure "0.1.4"]
                 [clj-time "0.12.2"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  :resource-paths ["resources"]

  :main dotted-line.core
  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-cprop "1.0.1"]
            [migratus-lein "0.2.6"]
            [lein-environ "1.0.0"]
            [lein-ancient "0.6.10"]]
  :target-path "target/%s/"
  :profiles
  {:uberjar {:omit-source true

             :aot :all
             :uberjar-name "dotted-line.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "1.1.4"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.5.0"]
                                 [pjstadig/humane-test-output "0.8.1"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.14.0"]]


                  :source-paths ["env/dev/clj" "test/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev {:env {:squiggly {:checkers [:eastwood :typed]
                                   :eastwood-exclude-linters [:unlimited-use]
                                   :eastwood-options {;; :builtin-config-files ["myconfigfile.clj"]
                                                      }}}}
   :profiles/test {}})
