package com.vampire.graphql.springbootgrapqlexample.service;
import java.io.File;
import	java.io.IOException;
import java.util.stream.Stream;

import com.vampire.graphql.springbootgrapqlexample.model.Book;
import com.vampire.graphql.springbootgrapqlexample.repository.BookRepository;
import com.vampire.graphql.springbootgrapqlexample.service.datafetcher.AllBooksDataFetcher;
import com.vampire.graphql.springbootgrapqlexample.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GraphQLService {

    @Autowired
    BookRepository bookRepository;

    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {
        loadDataIntoMysql();
        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoMysql() {
        Stream.of(
                new Book("123,", "Book of Clouds", "Kindle Edition",
                        new String[]{
                                "Chloe Aridjis"
                        }, "Nov 2017"),
                new Book("124,", "Cloud Arch & Engineering", "Orielly",
                        new String[]{
                                "Peter", "Sam"
                        }, "Jan 2015"),
                new Book("125,", "Java 9 Programming", "Orielly",
                        new String[]{
                                "Ve", "Ram"
                        }, "Dec 2019")
        ).forEach(book ->
            bookRepository.save(book)
        );
    }

    private RuntimeWiring buildRuntimeWiring() {

        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
