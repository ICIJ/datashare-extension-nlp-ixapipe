package org.icij.datashare.text.nlp.ixapipe;

import org.icij.datashare.PropertiesProvider;
import org.icij.datashare.text.DocumentBuilder;
import org.icij.datashare.text.NamedEntity;
import org.icij.datashare.text.nlp.AbstractModels;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;
import static org.icij.datashare.text.Language.ITALIAN;
import static org.icij.datashare.text.nlp.Pipeline.Property.STAGES;

public class IxapipePipelineTest {
    private IxapipePipeline ixapipePipeline;
    @Before
    public void setUp() {
        Properties props = new Properties();
        props.setProperty(STAGES.getName(), "POS,NER");
        AbstractModels.syncModels(false);
        ixapipePipeline = new IxapipePipeline(new PropertiesProvider(props));
    }

    @Test
    public void test_initialize() throws InterruptedException {
        ixapipePipeline.initialize(ITALIAN);

        assertThat(IxaPosModels.getInstance().isLoaded(ITALIAN)).isEqualTo(true);
        assertThat(IxaNerModels.getInstance().isLoaded(ITALIAN)).isEqualTo(true);
    }

    @Test
    public void test_process() throws Exception {
        ixapipePipeline.initialize(ITALIAN);
        List<NamedEntity> namedEntities = ixapipePipeline.process(DocumentBuilder.createDoc("docId").with("Grazie signor Foo Bar").build());

        assertThat(namedEntities).isNotNull();
        assertThat(namedEntities.size()).isEqualTo(0);
    }
}
