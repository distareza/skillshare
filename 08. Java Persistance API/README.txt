Create Maven Project :

	mvn archetype:generate \
		-DgroupId=com.jpa \
		-DartifactId=my-jpa-app \
		-DarchetypeArtifactId=maven-archetype-quickstart \
		-DinteractiveMode=false
	
	
Update pom.xml to use jdk 1.8
	...
	<properties>
	    <maven.compiler.target>1.8</maven.compiler.target>
	    <maven.compiler.source>1.8</maven.compiler.source>
	</properties>  
	...	
	  <build>
	  	<sourceDirectory>src/main/java</sourceDirectory>
	  	<plugins>
	  		<plugin>
	  			<artifactId>maven-compier-plugin</artifactId>
	  			<version>3.5.1</version>
	  			<configuration>
	  				<source>1.8</source>
	  				<target>1.8</target>
	  			</configuration>
	  		</plugin>
	  	</plugins>
	  </build>
	...

To Initiate MySQL Service :
	$>	docker-compose up -d

To Remove MySQL Service :
	$>	docker-compose down -d
	
	