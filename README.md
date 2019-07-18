# aion-web3j-example
This repo contains:

- [web3j-contract](https://github.com/jennijuju/aion-web3j-example/tree/master/web3j-contract): An Aion Java smart conract, wrote and tested using [Aion4j maven archetype](https://github.com/bloxbean/avm-archetype).
  - [Contract](https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-contract/src/main/java/org/aion/jennifer/HelloAvm.java)
  - [Contract ABI](https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-contract/target/web3j-contract-1.0-SNAPSHOT.abi)
  - [Compilerd Contract jar](https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-contract/target/web3j-contract-1.0-SNAPSHOT.jar)
- [web3j-aion](https://github.com/jennijuju/aion-web3j-example/tree/master/web3j-aion): Use [Web3j Aion](https://gitlab.com/web3j/web3j-aion) code generation to create the contract wrapper.
  - [Code Generator](https://github.com/jennijuju/aion-web3j-example/tree/master/web3j-aion/codegen)
  - [Binary Distribution](https://github.com/jennijuju/aion-web3j-example/tree/master/web3j-aion/codegen/build/distributions/web3j-aion-0.1.0-SNAPSHOT/bin)
- [web3j-project](https://github.com/jennijuju/aion-web3j-example/tree/master/web3j-project): A simple Java application that uses the generated web3j contract wrapper to deploy the contract to Aion Network and interact with it.
  - [Web3j Contract Wrapper](https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-project/src/HelloAvm.java)
  - [Deploy the Contract](https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-project/src/Deploy.java)
  - [Call the Contract](https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-project/src/Call.java)
  - [Send a Transaction to the Contract])(https://github.com/jennijuju/aion-web3j-example/blob/master/web3j-project/src/Send.java)
  
> Note: Checkout Xavier's [web3j-aion-samples project](https://gitlab.com/web3j/web3j-aion-samples) for a all-in-one for a fully configured Gradle project.

