package com.example.spring.graphql.api.controller;
import	java.sql.Types;

import com.example.spring.graphql.api.entity.Person;
import com.example.spring.graphql.api.repository.PersonRepository;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/person")
@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Value("classpath:graphql/person.graphqls")
    private Resource schemaResource;

    private GraphQL graphql;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = schemaResource.getFile();
        TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphql = GraphQL.newGraphQL(graphQLSchema).build();
    }

    @PostMapping("/addPerson")
    public String addPerson(@RequestBody List<Person> persons) {
        repository.saveAll(persons);
        return "record inserted " + persons.size();
    }

    @GetMapping("/findPersons")
    public List<Person> findPersons() {
        return this.repository.findAll();
    }

    @PostMapping("/getAllPerson")
    public ResponseEntity<Object> findAll(@RequestBody String query) {
        ExecutionResult result = graphql.execute(query);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/getPersonByEmail")
    public ResponseEntity<Object> findPersonByEmail(@RequestBody String query) {
        ExecutionResult result = graphql.execute(query);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    private RuntimeWiring buildWiring() {
        DataFetcher<List<Person>> fetcher1 = data -> {
            return repository.findAll();
        };

        DataFetcher<Person> fetcher2 = data -> repository.findByEmail(data.getArgument("email"));

        return RuntimeWiring.newRuntimeWiring().type("Query", typeWriting -> typeWriting
                .dataFetcher("findAllPerson", fetcher1)
                .dataFetcher("findPersonByEmail", fetcher2)).build();
    }
}
