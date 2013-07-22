## yr-scala-client

Wrapper for the yr.no weather api.
More information about the api can be found at: http://www.yr.no/ and http://tillegg.yr.no/application/scala-wrapper-for-the-yr-no-xml-api/

### To use the wrapper (before it's published):

#### If you want the source
- Clone this repo
- Step into the folder and do "sbt publish-local"
- Add "freddd" %% "yr-scala-client" % "1.0.0-SNAPSHOT" in your build.scala

#### If you want to use my github repo to fetch the snapshot
- Add: "resolvers += Resolver.url("fredd repository snapshots", url("https://github.com/freddd/ivy-mvn-repo/raw/master/ivy-snapshots/"))(Resolver.ivyStylePatterns)," in your build.scala
- Add "freddd" %% "yr-scala-client" % "1.0.0-SNAPSHOT" in your build.scala