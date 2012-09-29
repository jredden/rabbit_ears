
#!/bin/bash
java -cp /home/jredden/src/rabbit_ears/target/messages-0.1-jar-with-dependencies.jar service.app.BasicApp
java -cp /home/jredden/src/rabbit_ears/target/messages-0.1-jar-with-dependencies.jar service.app.ProducerConsumerApp
java -cp /home/jredden/src/rabbit_ears/target/messages-0.1-jar-with-dependencies.jar service.app.RouteApp
java -cp /home/jredden/src/rabbit_ears/target/messages-0.1-jar-with-dependencies.jar service.app.TopicApp
java -cp /home/jredden/src/rabbit_ears/target/messages-0.1-jar-with-dependencies.jar service.app.WorkApp
