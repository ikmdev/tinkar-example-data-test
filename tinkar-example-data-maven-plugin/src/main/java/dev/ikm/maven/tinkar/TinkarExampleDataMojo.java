package dev.ikm.maven.tinkar;

import dev.ikm.maven.toolkit.SimpleTinkarMojo;
import dev.ikm.tinkar.common.id.IntIds;
import dev.ikm.tinkar.common.id.PublicIds;
import dev.ikm.tinkar.composer.Composer;
import dev.ikm.tinkar.composer.Session;
import dev.ikm.tinkar.composer.assembler.ConceptAssembler;
import dev.ikm.tinkar.composer.assembler.PatternAssembler;
import dev.ikm.tinkar.composer.assembler.SemanticAssembler;
import dev.ikm.tinkar.composer.template.AxiomSyntax;
import dev.ikm.tinkar.composer.template.Comment;
import dev.ikm.tinkar.composer.template.Definition;
import dev.ikm.tinkar.composer.template.FullyQualifiedName;
import dev.ikm.tinkar.composer.template.GBDialect;
import dev.ikm.tinkar.composer.template.Identifier;
import dev.ikm.tinkar.composer.template.StatedAxiom;
import dev.ikm.tinkar.composer.template.StatedNavigation;
import dev.ikm.tinkar.composer.template.Synonym;
import dev.ikm.tinkar.composer.template.USDialect;
import dev.ikm.tinkar.entity.EntityService;
import dev.ikm.tinkar.terms.EntityProxy;
import dev.ikm.tinkar.terms.EntityProxy.Concept;
import dev.ikm.tinkar.terms.State;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.eclipse.collections.api.factory.Lists;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static dev.ikm.tinkar.terms.TinkarTerm.ACTIVE_STATE;
import static dev.ikm.tinkar.terms.TinkarTerm.BOOLEAN_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.BYTE_ARRAY_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.COMPONENT_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.COMPONENT_ID_LIST_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.COMPONENT_ID_SET_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.DESCRIPTION_NOT_CASE_SENSITIVE;
import static dev.ikm.tinkar.terms.TinkarTerm.DEVELOPMENT_MODULE;
import static dev.ikm.tinkar.terms.TinkarTerm.DEVELOPMENT_PATH;
import static dev.ikm.tinkar.terms.TinkarTerm.ENGLISH_LANGUAGE;
import static dev.ikm.tinkar.terms.TinkarTerm.FLOAT_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.GREATER_THAN_OR_EQUAL_TO;
import static dev.ikm.tinkar.terms.TinkarTerm.IMAGE_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.INACTIVE_STATE;
import static dev.ikm.tinkar.terms.TinkarTerm.INTEGER_FIELD;
import static dev.ikm.tinkar.terms.TinkarTerm.LESS_THAN;
import static dev.ikm.tinkar.terms.TinkarTerm.PREFERRED;
import static dev.ikm.tinkar.terms.TinkarTerm.PRIMORDIAL_STATE;
import static dev.ikm.tinkar.terms.TinkarTerm.ROLE_GROUP;
import static dev.ikm.tinkar.terms.TinkarTerm.ROOT_VERTEX;
import static dev.ikm.tinkar.terms.TinkarTerm.SOLOR_CONCEPT_ASSEMBLAGE;
import static dev.ikm.tinkar.terms.TinkarTerm.STRING;
import static dev.ikm.tinkar.terms.TinkarTerm.TINKAR_MODEL_CONCEPT;
import static dev.ikm.tinkar.terms.TinkarTerm.UNIVERSALLY_UNIQUE_IDENTIFIER;
import static dev.ikm.tinkar.terms.TinkarTerm.USER;
import static dev.ikm.tinkar.terms.TinkarTerm.VALUE_CONSTRAINT_PATTERN;

@Mojo(name = "generate-example-data", requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM, defaultPhase = LifecyclePhase.COMPILE)
public class TinkarExampleDataMojo extends SimpleTinkarMojo {

    private final long currentTimeMillis = System.currentTimeMillis();
    private Session session;
    private Concept SAMPLE_TINKAR_DATA;

    @Override
    public void run() {
        EntityService.get().beginLoadPhase();
        try {
            Composer composer = new Composer("Tinkar Example Data Composer");
            session = composer.open(
                    State.ACTIVE,
                    currentTimeMillis,
                    USER,
                    DEVELOPMENT_MODULE,
                    DEVELOPMENT_PATH);

            SAMPLE_TINKAR_DATA = createConcept(
                    "Tinkar Sample Data",
                    "67af3d9d-2d32-4036-9861-c052f3174134",
                    new Concept[] {TINKAR_MODEL_CONCEPT});

            createPatternOne();
            createPatternTwo();
            createPatternThree();
            createSampleHierarchy();
            createAxiomChangeTest();
            createExampleSemanticForRemainingPatterns();
            composer.commitSession(session);
        } finally {
            EntityService.get().endLoadPhase();
        }
    }

    private void createPatternOne() {
        EntityProxy.Pattern EXAMPLE_PATTERN_ONE = EntityProxy.Pattern.make("Tinkar Semantic Test Pattern 1", UUID.fromString("6604faf6-e914-49ca-a354-126f619f31ca"));
        Concept EXAMPLE_MEANING = createConcept("A test pattern for primitive data types", "ad6f4fdd-fee8-45db-a207-111dc4c939a9");
        Concept STRING_FIELD_MEANING = createConcept("An example String field", "c39286ba-55ed-4009-b7e1-48519fbd0e0a");
        Concept INTEGER_FIELD_MEANING = createConcept("An example Integer field", "38bcb9c6-cdce-4b02-a1bd-d976e3065b8a");
        Concept FLOAT_FIELD_MEANING = createConcept("An example Float field", "4276d7a6-2ae7-4ab4-8797-cffad22bf140");
        Concept BOOLEAN_FIELD_MEANING = createConcept("An example Boolean field", "87fc11f4-401d-47b2-9d64-82826f09524f");

        session.compose((PatternAssembler patternAssembler) -> patternAssembler.pattern(EXAMPLE_PATTERN_ONE)
                        .meaning(EXAMPLE_MEANING)
                        .purpose(EXAMPLE_MEANING)
                        .fieldDefinition(
                                STRING_FIELD_MEANING,
                                STRING_FIELD_MEANING,
                                STRING)
                        .fieldDefinition(
                                INTEGER_FIELD_MEANING,
                                INTEGER_FIELD_MEANING,
                                INTEGER_FIELD)
                        .fieldDefinition(
                                FLOAT_FIELD_MEANING,
                                FLOAT_FIELD_MEANING,
                                FLOAT_FIELD)
                        .fieldDefinition(
                                BOOLEAN_FIELD_MEANING,
                                BOOLEAN_FIELD_MEANING,
                                BOOLEAN_FIELD))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(EXAMPLE_PATTERN_ONE.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE))
                .attach((Synonym synonym) -> synonym
                        .text(EXAMPLE_PATTERN_ONE.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE))
                .attach((Definition definition) -> definition
                        .text("An example pattern for Tinkar String, Integer, Float, and Boolean data types")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE));

        Concept CONCEPT_FOR_SEMANTIC_1 = createConcept("First Semantic for Sample Pattern 1", "ad7e09a2-a492-4293-a68a-eb3018e5f23b");
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .pattern(EXAMPLE_PATTERN_ONE)
                .reference(CONCEPT_FOR_SEMANTIC_1)
                .fieldValues(objects -> objects.addAll(Lists.mutable.of("This is a test String", 1, 0.5f, true))));

        Concept CONCEPT_FOR_SEMANTIC_2 = createConcept("Second Semantic for Sample Pattern 1", "a25ed810-68bf-4c92-b010-a9492106484e");
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .pattern(EXAMPLE_PATTERN_ONE)
                .reference(CONCEPT_FOR_SEMANTIC_2)
                .fieldValues(objects -> objects.addAll(Lists.mutable.of("ThIs iS a DiFfeRenT tEsT StRiNg", 10, 7.5f, false))));

    }

    private void createPatternTwo() {
        EntityProxy.Pattern EXAMPLE_PATTERN_TWO = EntityProxy.Pattern.make("Tinkar Semantic Test Pattern 2", UUID.fromString("7222d538-9641-474a-94ce-72c5bf6462b3"));
        Concept EXAMPLE_MEANING = createConcept("A test pattern for component data types", "577ca159-5034-4c3b-8817-24a9de0d9b5c");
        Concept COMPONENT_FIELD_MEANING = createConcept("An example Component field", "3cd97362-ff6f-4337-b3f9-fb76d2ca4338");
        Concept COMPONENT_SET_FIELD_MEANING = createConcept("An example Component Set field", "990e5a92-cdc2-4e23-a68d-1f01345b8759");
        Concept COMPONENT_LIST_FIELD_MEANING = createConcept("An example Component List field", "f0847cd3-2034-43f5-b25f-2bd6e923d228");

        session.compose((PatternAssembler patternAssembler) -> patternAssembler.pattern(EXAMPLE_PATTERN_TWO)
                        .meaning(EXAMPLE_MEANING)
                        .purpose(EXAMPLE_MEANING)
                        .fieldDefinition(
                                COMPONENT_FIELD_MEANING,
                                COMPONENT_FIELD_MEANING,
                                COMPONENT_FIELD)
                        .fieldDefinition(
                                COMPONENT_SET_FIELD_MEANING,
                                COMPONENT_SET_FIELD_MEANING,
                                COMPONENT_ID_SET_FIELD)
                        .fieldDefinition(
                                COMPONENT_LIST_FIELD_MEANING,
                                COMPONENT_LIST_FIELD_MEANING,
                                COMPONENT_ID_LIST_FIELD))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(EXAMPLE_PATTERN_TWO.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE))
                .attach((Synonym synonym) -> synonym
                        .text(EXAMPLE_PATTERN_TWO.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE))
                .attach((Definition definition) -> definition
                        .text("An example pattern for Tinkar Component, Component Set, and Component List data types")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE));

        Concept CONCEPT_FOR_SEMANTIC_1 = createConcept("First Semantic for Sample Pattern 2", "016de8cc-b93c-4dab-a1d8-a2cc720f9351");
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .pattern(EXAMPLE_PATTERN_TWO)
                .reference(CONCEPT_FOR_SEMANTIC_1)
                .fieldValues(objects -> objects.addAll(
                        Lists.mutable.of(
                                EntityProxy.make("", PublicIds.of("845274b5-9644-3799-94c6-e0ea37e7d1a4")),
                                IntIds.set.of(
                                        EntityProxy.make("", PublicIds.of("700546a3-09c7-3fc2-9eb9-53d318659a09")).nid(),
                                        EntityProxy.make("", PublicIds.of("b42c1948-7645-5da8-a888-de6ec020ab98")).nid(),
                                        EntityProxy.make("", PublicIds.of("03004053-c23e-5206-8514-fb551dd328f4")).nid(),
                                        EntityProxy.make("", PublicIds.of("b17bde5d-98ed-5416-97cf-2d837d75159d")).nid(),
                                        EntityProxy.make("", PublicIds.of("b17bde5d-98ed-5416-97cf-2d837d75159d")).nid()
                                ),
                                IntIds.list.of(
                                        EntityProxy.make("", PublicIds.of("700546a3-09c7-3fc2-9eb9-53d318659a09")).nid(),
                                        EntityProxy.make("", PublicIds.of("351955ff-30f4-5806-a0a5-5dda79756377")).nid(),
                                        EntityProxy.make("", PublicIds.of("5a2e7786-3e41-11dc-8314-0800200c9a66")).nid()
                                )
                        )
                )));

        Concept CONCEPT_FOR_SEMANTIC_2 = createConcept("Second Semantic for Sample Pattern 2", "dde159ca-415e-4947-9174-cae7e8e7202d");
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .pattern(EXAMPLE_PATTERN_TWO)
                .reference(CONCEPT_FOR_SEMANTIC_2)
                .fieldValues(objects -> objects.addAll(
                        Lists.mutable.of(
                                EntityProxy.make("", PublicIds.of("700546a3-09c7-3fc2-9eb9-53d318659a09")),
                                IntIds.set.of(
                                        EntityProxy.make("", PublicIds.of("1f200ca6-960e-11e5-8994-feff819cdc9f")).nid(),
                                        EntityProxy.make("", PublicIds.of("e95b6718-f824-5540-817b-8e79544eb97a")).nid(),
                                        EntityProxy.make("", PublicIds.of("80710ea6-983c-5fa0-8908-e479f1f03ea9")).nid()
                                ),
                                IntIds.list.of(
                                        EntityProxy.make("", PublicIds.of("5c9b5844-1434-5111-83d5-cb7cb0be12d9")).nid(),
                                        EntityProxy.make("", PublicIds.of("65af466b-360c-58b2-8b7d-2854150029a8")).nid(),
                                        EntityProxy.make("", PublicIds.of("c1baba19-e918-5d2c-8fa4-b0ad93e03186")).nid(),
                                        EntityProxy.make("", PublicIds.of("6f96e8cf-5568-5e49-8a90-aa6c65125ee9")).nid(),
                                        EntityProxy.make("", PublicIds.of("6dfacbd5-8344-5794-9fda-bec95b2aa6c9")).nid()
                                )
                        )
                )));
    }

    private void createPatternThree() {
        EntityProxy.Pattern EXAMPLE_PATTERN_THREE = EntityProxy.Pattern.make("Tinkar Semantic Test Pattern 3", PublicIds.newRandom());
        EntityProxy.Concept EXAMPLE_MEANING = createConcept("A test pattern for image data type", PublicIds.newRandom().asUuidArray()[0].toString());
        EntityProxy.Concept IMAGE_FIELD_MEANING = createConcept("An example image field", PublicIds.newRandom().asUuidArray()[0].toString());


        session.compose((PatternAssembler patternAssembler) -> patternAssembler.pattern(EXAMPLE_PATTERN_THREE)
                        .meaning(EXAMPLE_MEANING)
                        .purpose(EXAMPLE_MEANING)
                        .fieldDefinition(
                                IMAGE_FIELD_MEANING,
                                IMAGE_FIELD_MEANING,
                                IMAGE_FIELD))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(EXAMPLE_PATTERN_THREE.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE))
                .attach((Synonym synonym) -> synonym
                        .text(EXAMPLE_PATTERN_THREE.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE))
                .attach((Definition definition) -> definition
                        .text("An example pattern for Tinkar Image data type")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE));

        // transform image to byte[]
        AtomicReference<byte[]> bytes = new AtomicReference<>();
        try {
            InputStream is = this.getClass().getResourceAsStream("/300px-HeartChambers.png");
            if (is == null) {
                throw new Exception("Unable to load 300px-HeartChambers.png");
            }
            byte[] byte_array = is.readAllBytes();
            is.close();
            bytes.set(byte_array);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        EntityProxy.Concept CONCEPT_FOR_SEMANTIC_1 = createConcept("First Semantic for Sample Pattern 3", PublicIds.newRandom().asUuidArray()[0].toString());

        // The UUID needs to be hardcoded as follows until komet is fixed to generically process image types
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .semantic(EntityProxy.Semantic.make(PublicIds.of("f43030a5-2324-4880-9292-c7d3c16b58d3")))
                .pattern(EXAMPLE_PATTERN_THREE)
                .reference(CONCEPT_FOR_SEMANTIC_1)
                .fieldValues(objects -> objects.addAll(
                        Lists.mutable.of(bytes.get()))));
    }

    private void createSampleHierarchy() {
                    /*

            G-G-G-G-GP          1  2
                                \  /
            G-G-G-GP              1   2
                                   \ /
            G-G-GP                  2   3
                                    |   |
            G-GP                1   2   3
                                |   | \ |
            GP                  1   2   3
                                |  / \  /
            P                   1  2   3
                                 \ |  /
            Self (ConceptA)        A
                                  / \
            C                    1   2
                                / \  |
            GC                 1  2  3
            */

        // Great-great-great-great-grandparent generation
        EntityProxy.Concept proxy = EntityProxy.Concept.make("GGGGGrandParent_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept ggggGrandparent1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString());
        proxy = EntityProxy.Concept.make("GGGGGrandParent_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept ggggGrandparent2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString());

        // Great-great-great-grandparent generation
        proxy = EntityProxy.Concept.make("GGGGrandParent_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept gggGrandparent1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{ggggGrandparent1, ggggGrandparent2});
        proxy = EntityProxy.Concept.make("GGGGrandParent_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept gggGrandparent2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString());

        // Great-great-grandparent generation
        proxy = EntityProxy.Concept.make("GGGrandParent_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept ggGrandparent2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{gggGrandparent1, gggGrandparent2});
        proxy = EntityProxy.Concept.make("GGGrandParent_3", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept ggGrandparent3 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString());

        // Great-grandparent generation
        proxy = EntityProxy.Concept.make("GGrandParent_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept gGrandparent1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString());
        proxy = EntityProxy.Concept.make("GGrandParent_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept gGrandparent2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{ggGrandparent2});
        proxy = EntityProxy.Concept.make("GGrandParent_3", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept gGrandparent3 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{ggGrandparent3});

        // Grandparent generation
        proxy = EntityProxy.Concept.make("GrandParent_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept grandparent1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{gGrandparent1});
        proxy = EntityProxy.Concept.make("GrandParent_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept grandparent2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{gGrandparent2});
        proxy = EntityProxy.Concept.make("GrandParent_3", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept grandparent3 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{gGrandparent2, gGrandparent3});

        // Parent generation
        proxy = EntityProxy.Concept.make("Parent_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept parent1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{grandparent1});
        proxy = EntityProxy.Concept.make("Parent_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept parent2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{grandparent2});
        proxy = EntityProxy.Concept.make("Parent_3", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept parent3 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{grandparent2, grandparent3});

        // Self
        proxy = EntityProxy.Concept.make("ConceptA", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept conceptA = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{parent1, parent2, parent3});

        // Child generation
        proxy = EntityProxy.Concept.make("Child_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept child1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{conceptA});
        proxy = EntityProxy.Concept.make("Child_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept child2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{conceptA});

        // Grandchild generation
        proxy = EntityProxy.Concept.make("GrandChild_1", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept grandchild1 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{child1});
        proxy = EntityProxy.Concept.make("GrandChild_2", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept grandchild2 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{child1});
        proxy = EntityProxy.Concept.make("GrandChild_3", PublicIds.newRandom().asUuidArray()[0]);
        EntityProxy.Concept grandchild3 = createConcept(proxy.description(), proxy.asUuidArray()[0].toString(), new EntityProxy.Concept[]{child2});
    }

    private void createAxiomChangeTest() {
        String roleParentConceptDescription = "Role Parent Concept for Axiom Change Test";
        EntityProxy.Concept roleParentConceptForAxiomChangeTest = EntityProxy.Concept.make(roleParentConceptDescription,
                PublicIds.of(UUID.nameUUIDFromBytes(roleParentConceptDescription.getBytes()),
                        // TODO: Remove forced UUID after tinkar-core / reasoner updates
                        UUID.fromString("3af1c784-8a62-59e5-82e7-767de930843b"))); // Force UUID of Concept Model Object Attribute

        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(roleParentConceptForAxiomChangeTest))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(roleParentConceptDescription + " - FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(roleParentConceptDescription + " - Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Definition definition) -> definition
                        .text("This is the Concept used to represent the Concept Model Object Attribute.")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(roleParentConceptForAxiomChangeTest.asUuidArray()[0].toString()))
                .attach((StatedNavigation statedNav) -> statedNav.parents(SAMPLE_TINKAR_DATA))
                .attach((StatedAxiom statedAxiom) -> statedAxiom.isA(SAMPLE_TINKAR_DATA));

        String roleConceptDescription = "Role Concept for Axiom Change Test";
        EntityProxy.Concept roleConceptForAxiomChangeTest = EntityProxy.Concept.make(roleConceptDescription,
                PublicIds.of(UUID.nameUUIDFromBytes(roleConceptDescription.getBytes())));

        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(roleConceptForAxiomChangeTest))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(roleConceptDescription + " - FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(roleConceptDescription + " - Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Definition definition) -> definition
                        .text("This is the Concept used for testing reasoning capabilities and logical axiom changes.")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(roleConceptForAxiomChangeTest.asUuidArray()[0].toString()))
                .attach((AxiomSyntax owlAxiom) -> owlAxiom
                        .text(String.format("SubObjectPropertyOf(:[%s] :[%s])", roleConceptForAxiomChangeTest.asUuidArray()[0], roleParentConceptForAxiomChangeTest.asUuidArray()[0])));

        String parentConceptDescription = "Parent Concept for Axiom Change Test";
        EntityProxy.Concept parentConceptForAxiomChangeTest = EntityProxy.Concept.make(parentConceptDescription, PublicIds.of(UUID.nameUUIDFromBytes(parentConceptDescription.getBytes())));

        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(parentConceptForAxiomChangeTest))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(parentConceptDescription + " - FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(parentConceptDescription + " - Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Definition definition) -> definition
                        .text("This is the Concept used for testing reasoning capabilities and logical axiom changes.")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(parentConceptForAxiomChangeTest.asUuidArray()[0].toString()))
                .attach((AxiomSyntax owlAxiom) -> owlAxiom
                        .text(String.format("SubClassOf(:[%s] ObjectIntersectionOf(:[%s] ObjectSomeValuesFrom(:[%s] ObjectSomeValuesFrom(:[%s] :[%s]))))",
                                parentConceptForAxiomChangeTest.asUuidArray()[0], SAMPLE_TINKAR_DATA.asUuidArray()[0], ROLE_GROUP.asUuidArray()[1], roleConceptForAxiomChangeTest.asUuidArray()[0], ACTIVE_STATE.asUuidArray()[0])));

        String child1ConceptDescription = "Child 1 Concept for Axiom Change Test";
        EntityProxy.Concept child1ConceptForAxiomChangeTest = EntityProxy.Concept.make(child1ConceptDescription, PublicIds.of(UUID.nameUUIDFromBytes(child1ConceptDescription.getBytes())));

        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(child1ConceptForAxiomChangeTest))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(child1ConceptDescription + " - FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(child1ConceptDescription + " - Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Definition definition) -> definition
                        .text("This is the Concept used for testing reasoning capabilities and logical axiom changes.")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(child1ConceptForAxiomChangeTest.asUuidArray()[0].toString()))
                .attach((AxiomSyntax owlAxiom) -> owlAxiom
                        .text(String.format("SubClassOf(:[%s] ObjectIntersectionOf(:[%s] ObjectSomeValuesFrom(:[%s] ObjectSomeValuesFrom(:[%s] :[%s])) ObjectSomeValuesFrom(:[%s] ObjectSomeValuesFrom(:[%s] :[%s]))))",
                                child1ConceptForAxiomChangeTest.asUuidArray()[0], SAMPLE_TINKAR_DATA.asUuidArray()[0], ROLE_GROUP.asUuidArray()[1], roleConceptForAxiomChangeTest.asUuidArray()[0], ACTIVE_STATE.asUuidArray()[0], ROLE_GROUP.asUuidArray()[1], roleConceptForAxiomChangeTest.asUuidArray()[0], PRIMORDIAL_STATE.asUuidArray()[0])));

        String child2ConceptDescription = "Child 2 Concept for Axiom Change Test";
        EntityProxy.Concept child2ConceptForAxiomChangeTest = EntityProxy.Concept.make(child2ConceptDescription, PublicIds.of(UUID.nameUUIDFromBytes(child2ConceptDescription.getBytes())));

        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(child2ConceptForAxiomChangeTest))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(child2ConceptDescription + " - FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(child2ConceptDescription + " - Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Definition definition) -> definition
                        .text("This is the Concept used for testing reasoning capabilities and logical axiom changes.")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(child2ConceptForAxiomChangeTest.asUuidArray()[0].toString()))
                .attach((AxiomSyntax owlAxiom) -> owlAxiom
                        .text(String.format("SubClassOf(:[%s] ObjectIntersectionOf(:[%s] ObjectSomeValuesFrom(:[%s] ObjectSomeValuesFrom(:[%s] :[%s])) ObjectSomeValuesFrom(:[%s] ObjectSomeValuesFrom(:[%s] :[%s]))))",
                                child2ConceptForAxiomChangeTest.asUuidArray()[0], SAMPLE_TINKAR_DATA.asUuidArray()[0], ROLE_GROUP.asUuidArray()[1], roleConceptForAxiomChangeTest.asUuidArray()[0], ACTIVE_STATE.asUuidArray()[0], ROLE_GROUP.asUuidArray()[1], roleConceptForAxiomChangeTest.asUuidArray()[0], INACTIVE_STATE.asUuidArray()[0])));
    }

    private void createExampleSemanticForRemainingPatterns() {
        String conceptDescription = "Concept for Example Semantics";
        EntityProxy.Concept conceptWithExampleSemantics = EntityProxy.Concept.make(conceptDescription, PublicIds.of(UUID.nameUUIDFromBytes(conceptDescription.getBytes())));

        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(conceptWithExampleSemantics))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(conceptDescription + " - US FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(conceptDescription + " - GB FQN")
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(new GBDialect().acceptability(PREFERRED)))
                .attach((Synonym synonym) -> synonym
                        .text(conceptDescription + " - US Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(conceptDescription + " - GB Synonym")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(new GBDialect().acceptability(PREFERRED)))
                .attach((Definition definition) -> definition
                        .text("This is the Concept used to aggregate additional example Semantics. Example Semantics are intended to provide at least one example Semantic for each Pattern in in the tinkar-starter-data dataset. The additional example Semantics for this Concept are intended to exercise any remaining Patterns.")
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(conceptWithExampleSemantics.asUuidArray()[0].toString()))
                .attach((StatedNavigation statedNavigation) -> statedNavigation
                        .parents(SAMPLE_TINKAR_DATA))
                .attach((StatedAxiom statedAxiom) -> statedAxiom
                        .isA(SAMPLE_TINKAR_DATA))
                .attach((Comment comment) -> comment
                        .text("This is an example Comment."))
                .attach((AxiomSyntax owlAxiom) -> owlAxiom
                        .text(String.format("SubClassOf(:[%s] :[%s])", conceptWithExampleSemantics.asUuidArray()[0], SAMPLE_TINKAR_DATA.asUuidArray()[0])));

        // Add Solor Concept Assemblage Membership Semantic
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .pattern(SOLOR_CONCEPT_ASSEMBLAGE)
                .reference(conceptWithExampleSemantics)
                .fieldValues(List::clear));

        // Add Value Constraint Pattern Semantic
        session.compose((SemanticAssembler semanticAssembler) -> semanticAssembler
                .pattern(VALUE_CONSTRAINT_PATTERN)
                .reference(conceptWithExampleSemantics)
                .fieldValues(fieldVals -> fieldVals
                        .with(ROOT_VERTEX)
                        .with(GREATER_THAN_OR_EQUAL_TO)
                        .with(0.0)
                        .with(LESS_THAN)
                        .with(11.11)
                        .with("Example Units")));
    }

    private Concept createConcept(String description, String uuidStr) {
        return createConcept(description, uuidStr, new Concept[] {SAMPLE_TINKAR_DATA});
    }

    private Concept createConcept(String description, String uuidStr, Concept[] parent) {
        Concept concept = Concept.make(description, UUID.fromString(uuidStr));
        session.compose((ConceptAssembler conceptAssembler) -> conceptAssembler.concept(concept))
                .attach((FullyQualifiedName fqn) -> fqn
                        .text(concept.description())
                        .language(ENGLISH_LANGUAGE)
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .attach(usDialect()))
                .attach((Synonym synonym) -> synonym
                        .text(concept.description())
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Definition definition) -> definition
                        .text(concept.description())
                        .caseSignificance(DESCRIPTION_NOT_CASE_SENSITIVE)
                        .language(ENGLISH_LANGUAGE)
                        .attach(usDialect()))
                .attach((Identifier identifier) -> identifier
                        .source(UNIVERSALLY_UNIQUE_IDENTIFIER)
                        .identifier(concept.asUuidArray()[0].toString()))
                .attach(new StatedNavigation()
                        .parents(parent))
                .attach(new StatedAxiom()
                        .isA(parent));
        return concept;
    }

    private USDialect usDialect() {
        return new USDialect().acceptability(PREFERRED);
    }
}