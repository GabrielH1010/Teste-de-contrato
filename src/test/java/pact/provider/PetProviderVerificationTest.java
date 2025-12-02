package pact.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.provider.junitsupport.State;

@Provider("PetProvider")
@PactFolder("target/pacts")
public class PetProviderVerificationTest {

    @BeforeEach
    void before(PactVerificationContext context) {
        System.out.println("Subindo servidor mock...");
        PetMockProvider.start();

        try {
            // Aguarda o Spark iniciar completamente
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.print("Erro ao iniciar o servidor: " + e);
        }

        System.out.println("Configurando alvo de verificação...");
        context.setTarget(new HttpTestTarget("localhost", 9999));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Pet existe")
    public void setupState() {
        System.out.println("Provider pronto para interagir");
    }
}
