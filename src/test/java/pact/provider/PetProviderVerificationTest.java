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

//Garante que leia o arquivo gerado
@PactFolder("target/pacts")
public class PetProviderVerificationTest {

    @BeforeEach
    void before(PactVerificationContext context) {
        //Sobe o servidor mock antes da verificação
        System.out.println("Subindo servidor mock");
        PetMockProvider.start();

        // Configura o alvo HTTP do teste
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
