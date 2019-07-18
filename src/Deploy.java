
import org.web3j.aion.VirtualMachine;
import org.web3j.aion.crypto.Ed25519KeyPair;
import org.web3j.aion.protocol.Aion;
import org.web3j.aion.tx.AionTransactionManager;
import org.web3j.aion.tx.gas.AionGasProvider;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;

public class Deploy {

    //private static String NODE_ENDPOINT = System.getenv("NODE_ENDPOINT");
    //private static String PRIVATE_KEY = System.getenv("PRIVATE_KEY");

    private static final Aion aion = Aion.build(new HttpService(""));

    private static final TransactionManager manager = new AionTransactionManager(
            aion, new Ed25519KeyPair(""), VirtualMachine.AVM
    );


    public static void deployAndCallContract() throws Exception {

        // Deploy the contract
        final HelloAvm helloContract = HelloAvm.deploy(aion, manager, AionGasProvider.INSTANCE).send();
        System.out.println("tx receipt:"+ helloContract.getTransactionReceipt());

    }

    public static void main(String [] args) throws Exception {
        deployAndCallContract();

    }
}
