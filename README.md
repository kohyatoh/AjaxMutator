### Overview
AjaxMutator is software to conduct [Mutation Testing (or Analysis)](http://en.wikipedia.org/wiki/Mutation_testing)
on JavaScript Web Applications.

Mutation analysis consists of two parts, namely,  
1) Creating mutants (faulty program) and 2) Running test on mutants

Using AjaxMutator, you can  
1) Specify what kind of mutation to apply and 2) Running JUnit test cases over mutants

You are also able to  
1) Define your own mutation operator and 2) Combining other test framework  
by overriding build-in classes

### Build
Requirement:

- JDK 1.7 or higher
- [Apache Maven](http://maven.apache.org/) installed
- [OWASP ZAP](https://code.google.com/p/zaproxy/) 2.3.1

Run following command to configure Classpath for JavaFX (For detail, see http://zenjava.com/javafx/maven/fix-classpath.html)
> mvn com.zenjava:javafx-maven-plugin:2.0:fix-classpath

Run following command to install zap-api.jar
> mvn install:install-file -Dfile=./zap-api-v2-7.jar -DgroupId=org.zaproxy -DartifactId=clientapi -Dversion=2.7 -Dpackaging=jar

And then, you can compile AjaxMutator by
> mvn compile

### Usage
See [/sample](https://github.com/knishiura-lab/AjaxMutator/tree/master/sample) to check how to use
AjaxMutator by running example.

### License
MIT

OWASP ZAP is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

### Publication
**Mutation Analysis for JavaScript Web Applications Testing**  
Kazuki Nishiura, Yuta Maezawa, Hironori Washizaki and Shinichi Honiden  
The 25th International Conference on Software Engineering and Knowledge Engineering (SEKE'13), 159-165, June 2013

[Our GitHub page](http://knishiura-lab.github.io/AjaxMutator/) and
[Wiki](https://github.com/knishiura-lab/AjaxMutator/wiki) may contain more details.
