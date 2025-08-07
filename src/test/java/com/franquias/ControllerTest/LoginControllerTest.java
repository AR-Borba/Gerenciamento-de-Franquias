// package com.franquias.ControllerTest;

// import static org.junit.Assert.assertTrue;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.franquias.Controller.LoginController;
// import com.franquias.Model.entities.Usuários.Gerente;
// import com.franquias.Persistence.DonoPersistence;
// import com.franquias.Persistence.GerentePersistence;
// import com.franquias.Persistence.VendedorPersistence;

// public class LoginControllerTest {
//     LoginController loginController;
//     DonoPersistence donoPersistence;
//     GerentePersistence gerentePersistence;
//     VendedorPersistence vendedorPersistence;

//     @BeforeEach
//     void setUp() {
//         donoPersistence = new DonoPersistence();
//         gerentePersistence = new GerentePersistence();
//         vendedorPersistence = new VendedorPersistence();
//         loginController = new LoginController(null, donoPersistence, gerentePersistence, vendedorPersistence);
//     }

//     @Test
//     void testaEmailJaExistente() {
//         String emailTeste = "teste@gmail.com";
//         gerentePersistence.adicionarGerente(new Gerente("Gerente Teste", emailTeste, "1234", "11111111111"));

//         assertTrue(loginController.emailJaExiste(emailTeste));
//     }

//     @Test
//     void testaEmailNaoExistente() {
//         String emailTeste = "teste1@gmail.com";
//         gerentePersistence.adicionarGerente(new Gerente("Gerente Teste", emailTeste, "1234", "11111111111"));

//         assertTrue(loginController.emailJaExiste("teste@gmail.com"));
//     }
// }
