package org.aion.web3;

import org.web3j.aion.VirtualMachine;
import org.web3j.aion.crypto.Ed25519KeyPair;
import org.web3j.aion.protocol.Aion;
import org.web3j.aion.tx.AionTransactionManager;
import org.web3j.aion.tx.gas.AionGasProvider;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;


public class Deploy {

    private static String NODE_ENDPOINT = System.getenv("NODE_ENDPOINT");
    private static String PRIVATE_KEY = System.getenv("PRIVATE_KEY");

    private static final Aion aion = Aion.build(new HttpService(NODE_ENDPOINT));

    private static final TransactionManager manager = new AionTransactionManager(
            aion, new Ed25519KeyPair(PRIVATE_KEY), VirtualMachine.AVM
    );


    public static void main(String [] args) throws Exception {
        // Deploy the contract
        final Counter counterContract = Counter.deploy(aion, manager, AionGasProvider.INSTANCE, 1).send();
        System.out.println("Tx Hash:"+ counterContract.getTransactionReceipt());
        System.out.println("Contract Address: " + counterContract.getContractAddress());
    }

}
