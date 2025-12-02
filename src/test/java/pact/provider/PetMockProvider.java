package pact.provider;

import pact.utils.Utils;

import static spark.Spark.*;

public class PetMockProvider {

    public static void start() {
        System.out.println("Inicializando o Mock Provider");

        //Mesma porta usada no Provider test
        port(9999);

        post("/pet", (request, response) -> {
            response.type("application/json");
            response.status(200);
            return Utils.lerArquivoJson("bodyAddPet.json").toString();
        });

        System.out.println("Mock Provider rodando em http://localhost:9999");
    }
}
