import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.model.inprocess.InProcessEmbeddingModel;
import dev.langchain4j.store.embedding.RelevanceScore;

import java.io.IOException;

import static dev.langchain4j.model.inprocess.InProcessEmbeddingModelType.ALL_MINILM_L6_V2;

public class InProcessEmbeddingModelExamples {

    static class Pre_Packaged_In_Process_Embedding_Model_Example {

        public static void main(String[] args) throws IOException {

            String text = "Let's demonstrate that embedding can be done within a Java process and entirely offline.";

            // requires "langchain4j-embeddings-all-minilm-l6-v2" Maven/Gradle dependency, see pom.xml
            EmbeddingModel inProcessEmbeddingModel = new InProcessEmbeddingModel(ALL_MINILM_L6_V2);
            Embedding inProcessEmbedding = inProcessEmbeddingModel.embed(text);

            // Now let's compare with the embedding generated by HuggingFace
            EmbeddingModel huggingFaceEmbeddingModel = HuggingFaceEmbeddingModel.builder()
                    .modelId("sentence-transformers/all-MiniLM-L6-v2")
                    .accessToken(System.getenv("HF_API_KEY"))
                    .build();
            Embedding huggingFaceEmbedding = huggingFaceEmbeddingModel.embed(text);

            System.out.println(RelevanceScore.cosine(
                    inProcessEmbedding.vector(),
                    huggingFaceEmbedding.vector()
            ));
            // 1.0000000009816103 <- this indicates that the embedding created by the all-MiniLM-L6-v2 model in-process (offline)
            // is practically identical to those generated using the HuggingFace API.
        }
    }

    static class Custom_In_Process_Embedding_Model_Example {

        public static void main(String[] args) throws IOException {

            String text = "Let's demonstrate that embedding can be done within a Java process and entirely offline.";

            // requires "langchain4j-embeddings" Maven/Gradle dependency, see pom.xml
            // See InProcessEmbeddingModel's javadoc for more info
            EmbeddingModel inProcessEmbeddingModel = new InProcessEmbeddingModel("/home/me/model.onnx");

            Embedding inProcessEmbedding = inProcessEmbeddingModel.embed(text);
            System.out.println(inProcessEmbedding);
        }
    }
}
