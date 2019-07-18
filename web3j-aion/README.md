# Web3j Aion Integration

This folder contains Web3j and its binary distribution for code generator.

## Get Web3j

```sh
git clone https://gitlab.com/web3j/web3j-aion.git
```

## Building the binary distribution

Do the following:

```sh
cd web3j-aion-master
./gradlew distZip
cd codegen/build/distributions
unzip web3j-aion-0.1.0-SNAPSHOT.zip
cd web3j-aion-0.1.0-SNAPSHOT/bin/
```
Then you will see `web3j-aion`. 

## Generate the contract wrapper

```sh
./web3j-aion -a `You contract abi` -b `Your contract jar` -o 'Desired output directory` -p 'basic package name`
```
If you cloned this repo and have the contract folder, an example for getting the wrapper is:

```sh
./web3j-aion -a ../../../../../../web3j-contract/target/web3j-contract-1.0-SNAPSHOT.abi -b ../../../../../../web3j-contract/target/web3j-contract-1.0-SNAPSHOT.jar -o ../../../../../../web3j-project/src/  -p ""
```

where we grab the `contract abi` and `compiled contact jar` from our AVM project, and saved the generated wrapper in our project for later use.






