package org.web3j.aion.tx

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.web3j.aion.VirtualMachine
import org.web3j.aion.crypto.AionTransaction
import org.web3j.aion.crypto.Ed25519KeyPair
import org.web3j.aion.protocol.Aion
import org.web3j.aion.protocol.mock

class AionTransactionManagerTest {

    private val aion = mockk<Aion>(relaxed = true)
    private val keyPair = Ed25519KeyPair(PRIVATE_KEY)
    private val manager = AionTransactionManager(
        aion, keyPair, VirtualMachine.AVM
    )

    @Test
    fun `sign default raw transaction`() {

        every {
            // Mock eth_getTransactionCount call since nonce is not specified
            aion.ethGetTransactionCount(address = any(), defaultBlockParameter = any())
        } returns mock("0x2c")

        every {
            aion.ethGasPrice() // Mock eth_gasPrice since nrgPrice is not specified
        } returns mock("0x2540be400")

        val aionTransaction = AionTransaction(
            data = "0x210009736574537472696e6721000a48656c6c6f2074657374",
            timestamp = 1560507493830000,
            nrg = 5000000.toBigInteger()
        )
        assertThat(manager.sign(aionTransaction)).isEqualTo(
            "0xf8952c808099210009736574537472696e6721000a48656c6c6f207465737487058b45f6d6cd70834c4b408800000002540be40001b86008fe2bf5757b8261d4937f13b5815448f2144f9c1409a3fab4c99ca86fff8a3609bedc551b7078a5a821624438d2d057682958486b7099163e449f90f9944bf14e8382e59000a0f0fc1a869284237659a1373c1ccb61b911f8b51e84f17e2601"
        )
    }

    @Test
    fun `sign raw transaction constructor AVM`() {
        val aionTransaction = AionTransaction(
            data = "0x00001c2a504b03041400080808002d72be4e000000000000000000000000140004004d4554412d494e462f4d414e49464553542e4d46feca0000f34dcccb4c4b2d2ed10d4b2d2acecccfb35230d433e0e5f24dccccd375ce492c2eb652c82f4ad72b4f4d32ced24b042ad0f348cdc9c9772ccbe5e5e2e50200504b070877a5f6923f0000003e000000504b03041400080808002d72be4e0000000000000000000000001d0000006f72672f776562336a2f61696f6e2f48656c6c6f41766d2e636c6173738554eb72db44183deb5ca408b54e9c4b2f2925a569eb38100185124828c44e43429da4ad43426aa0ace51d47b12c1969ed4ede810780b70830937a4aa77f99e19d60f85692272431ad7e68f5ed77f67cb7b3faeb9fe72f01dc8160b8e00735eba9a8dcdeb7b8e37bd6aa705d7fa9ddd0c01886f7799b5b2ef76ad666655fd852431fc3b92e664eb9196e1ca31cafedd785b52ee49e5f5de55ed515e174d1f7ebada6864186abaf866ad01906633cc340e3a02403864cf1f8146d385e6d81508b8ee7c8bb0c7dd9996d13e770de403fd20c7ac80fa2041986a2758a32d5916138cfdb0d2beffa76dddee38ea7618c416b129f743d86f1ecd9308a7902170c8ce322e5530b84900c377b217be5a8c5f1998e2b0cd3674a8f7105dfb3b95ce1b6f483030d5769240d5e17f1f68e23f7e82b94dc9321c3cfff8dfc8a5e2f9ccde6ff0e6e1d34450f78b9787af4274a4c280adc754b8e140b26a670cdc05b783b3d88ebca9acc90c26ed00c6a42c69c0c63d91e6d32f106cc21a430c3305a680581f0e45418f9a69c50356f3603165185c754e90df1f424caca1007a1f445db4da4d19dfff6ba8edb0cfd0d9a3a2dd999729ee1ba127e2479258b562802d7a958bce2584bf9b56561fb551168f898a648152c73c94d7c823b4a0a9f9214b2e57c2cbb4503f3f88cae4a353a12377583378489cf9155ce2f748c9cb84a710d1a0aa462f1638bbbe129f5751bfed8c43dac1858c697264694c25358d37199ca8fa36d7a222633518c83ad9bb88c4905dc7c4d89f7bca4c4874427bc13740c13bd345ece9b2861cbc0237cade396895b2a680adfe8983331a72e4a0a65aa2a106d114813dfa99cc7f13d35bd40fcf4eb28496ed7d779738b575cb2cd35cf1341c1e56128a80b46c96f05b65871946f38effb9246cc9b7153435c23fa7e9215019568e88bfe52eaead3fb07b2466865b40ee49e61f857a86748f52d71672227309819ede052d74fed4afca3c9712df71c3781c30440452680c98420fd1b08f22e4034f4d905520312e095846938f73b7211f8fd081cc7d4f1418f9c3e246837a78fe8df1cfb7f421f150dd4ffc0fc6e070bcf70f77eee084bc5d93fa1f5bdd00f6733f923acfe02a383af62fb7e648fe4768fb0f164a783071d6cd3f64e17b61bdb8f4fc1be55a73b78c20ea30e73552706fec6450df31a96d313e909caa542db06b935cad2c025d864a730802aad63e49f26df9bd1fb9de8fddebf504b07084bf3af02770300006c060000504b03041400080808002d72be4e0000000000000000000000002b0000006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249457863657074696f6e2e636c6173734d8e3d4b03411086df399353ce4413c1269d85e007b89d8d12d0a810b13278fd242ec7c86657f6f6827fcb4ab0f007f8a3c48929cc14f3f1ce3b0ff3fdf3f905e01c7dc26188956109def0626e9ada462753c3533157d7e3dbb7997d4dbadc0411062fbc60e3d857e6b1f149e6766dbf41e8af5f9c2dcd849e02855d6963ade2d3f88640f76dac4299f9a5784943c2fed1c33f7e92a2f8eae2b8eca0c07681163a84ee28f83ab14f25bbc6125aa3f0aca5988426ceec9d388b0364ea553006c8a14f6347a70c5bdad112a57957953dd532adf9c9e907baefab67d0fbf3b67f01504b07080969797ee00000001e010000504b03041400080808002d72be4e000000000000000000000000290000006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249456e636f6465722e636c6173739598df771b4715c7ef4a2bed5a7192956c494e223b92d31fb27e4489da2885d4054b8a8b1c3b2948a44d5ca8d7f6da96ab48aeb44ee31692425d28014a43e92f28a4fc38f8151ea02e04c1034fbcf3c039bc7178e13fe09c52716776a59ddd952a350ff6ceccbd773edfb9777e387ffbf80f4d00c8c0cf393851abafa7e472ad9a92af5f4b6d37947aa5bc9c9297cba9996ce17c75a5b6aad405e0389036e5eb72aa2257d753979637951555002707870dab93c48083a1ec95d2f96716668a1738e00a4e9ca7c581fbd172b5ac3ec681333a75791886c0e3011e0e707050a1ce97aa4a764755387045b3538b599ccde8afd52a8a5c254357e99063312b8097035fc724b721d7e51555a913a31c353ad4192c6ed4ea2a19285a0217aaaab2aef914e890c1325fabae93fe39ec7781f6afc5069dadd4641a74967a1e36d6868e08701cadd7c867a986f364cb6a83031ead0bc31081490f84e104ba75e2e56bdbcb152a3faf511a01b521011ec4de55fa5daa11be76ccfcd4dc304c41cc035188b3ab421674a65e977770f9a38bdab2deff89d9beb1a26ca93828408a8330f50d5794eababa11beb6dd50c36b65355cae86d3e1650cdd10e13407fee8bc815a54ebe5eafa392dc30f79e0143c6c9252dc69a8ca35013258253289be52db42ba181b43abad73852e5d0512f811f88c07cec267716a6b891862db85725500acb9a0bd500c53ad5c46cce5620c6b45e3b7168d61a0958ecf543ac6e89c253aad0e6358ab9e514b1918e35a311865a22d3007816eab6ea91bad5b808b1c88eb8a4aaa81d60bda0dc313f0450f5c822fb1fb616675b5ae34d0c61b9d27d5a1b7b5c007981e012e63875aeb14d8303c45e23d095748d96846e1e7e546b8b616deae2a37b6307dcaaa5e4a222cb292d379a64e85e8222dd46148c2490f38e019a459abc8aaaa54d3797ace50fd43c4aea059cab04c2c57f06c59242783c2261ca39b6a834c7095ba3d0ad3c4ad4cddb04e9ee5e008e366ad14e298a38e9f8719e258a38e39019e63cb239d67eb873815a9531672c4699b3a150578de0c69ae2ae256a06e79384fdc5ea46e0501be6e9e8bad26e2344b9dbe0005e2f40dea342bc0cbe6e5660a94f8cc519f59789cf87c9bfacc09f01dac31c6c75497c42b4fbde6e002f1fa3ef5ca0bf0037626adfe749f6074b16bc50ec33c2c90186fe02ee96222c09bac64bdb6f4a03e0c6a29d46128c19749bc77f1fc358f0af0133ce273b56a4395abea65b9b28d95c2e7303076175579e5d90579ab24d363d853ac6dd75794d9724581d3188dc7f39f83a3e006bc0ae1a7d872800813f8136f32ecf919f678f13787bf5db10f60f8b7f4ca18828370481f3e0a4e340738e4ba27cec79d7c29ce1f2dc57fa3db1d0649b79bd3ed829a9d8bd8bd0b02bf07bcf38ee6c0c15dfc1901470b7ce0e004f2137f702dc2459b4ed2c46d022330aac78da30812f7b89bc67593b892f882175a3731aceba8fed106f243a0bba3d0c7310863ba634e773c2b5247913a8e198e92d4fe763301056bc023b8745ac09770fdc97b429586ee890b09a7e42e25f809e9911747e1d8ad57ef9412ae09e954a7e19e90a29d863021853b0d71421aeb3424b4938c161a8a464b9c687fb669c631eb1a4d01e5119a69226f721fee9b8e3b25b23a2146648811196244862c22ef8707f4b0afa048f2e4d8a1221fd8874416958aa834c22a8db04a23acd208ab3462521a31298d989446ac4af104d691540422897c8a8bfd1a46b5ba94d2a854f296f620176b7a6fb57e0507ff08a7aef8d21fc0993fc79aae257e49b3f3a25dacc9140c9ad3af9833ee8e35f7e11c53d31e7006bc1f4192d4f01039a275820f71adc91e7cce46e04382a73f99c0d78bc039ed7e2c146bfe028ec5c763a1878c5db6ebe0769ddc5eebdf0c5a06dc01efc710a53bab85bb9d17e073da36eb6c3ea34bdb80ff454143e4c2d065bcafcbb8659331823236bbcad830848cf415f22011f2b06131ce2f615b6b50518ebdd63f1851878127a2623aed14d2e24dd597767410dad1c168cf5868cf7c2a5abc2075dabfe8b4776db47ea4bddd8d566068fd7d691f27b4278dbd4d694f1afb7bdc45db861a3769336af8bdd687bdd514500ddec29a1aee009e0244cd7f6c6a02a8e6efddd448222327d057ce1d2227651c299a9e9471ac688252c6d1a2294a19c74b7c5ca01d634687483b24a343d27c44a6873ae94dba30e25eab665f98b8be306fe0c2e093464ff35f7159c8f1f84bdbc20471615eeb97e660df75b9100b9dc2c33cc3c7c7fdbc29dbd864938d4d36d77ede9ceadff54ef5022ac2a713936a77d7548f0d94eab1fea98e854ee33d721625057873c6b16d4a38b64df9c6b629ddd836651bdbe664638739d701fed3a51adf837aaad730d1e482bd605b98082e4c26f67b282e2498733e824309762112fa4224f0a649586e9a2170043e82b476d1e09b519f73539f73c636e724ce99c039af2e48e144f3ae9e90a74942a48866374966e2ad3305f14efb1fbea770aa96f6527b92cc4a10be025fd5e77d592feb27c8bc47dcccbca75108bd6733c274acf927f05d7466783f4ff21a4ae26f3f9fde87a522f94e3797a67779bcb7fe950c49de7d589d373d17f9632dbc8f5c022e2c3e12d7f0ad7abc855408b4d6065a878dc1807c7d8036ed40bede40152b50a50d740daa83018df401dab2038df406aa5b81ea6da006be8606021aed0374dd0e34da1be88615e8461b68075e180cc8df07e86b76207f6fa09b56a09b6da05bf84e1f0828d807e89b76a0606fa05d2bd06e1be815f8d66040813e40afda8102bd816e5b816eb781be0bdf1b0c68ac0fd06b76a0b1de40af5b815e6f03dd811f0e0614e903f4233b50a437d05b56a0b7da406fc33b83014df601fab11d68b237d07b56a0f734200ff94f261d684bbfae8bc7ee8917934ec448f2a152d2c55cc14977fb0a1632bc33e3f2bb08d48998df95765232579adc11fad75286df7521db3f9306d30170b49f0be4efc2f7c91ff6e0fa3f504b070831a65f2973080000b7170000504b03041400080808002d72be4e000000000000000000000000290000006f72672f61696f6e2f61766d2f757365726c69622f6162692f4142494465636f6465722e636c617373ad9a0b7c1c451dc7ff93bbcb6e2edbf6f26cda24ed252d36495b425b0a96b69034d7684a9a02498b092adde6b6c995cb6dbd6c4aab88a01451f1d5aa8020d4021690a71652a1147c2be25b514114df8a5a8ae203acc4ffccceeecdec6ddae54c3f4d2f3b3bf3ffffbef3f8cdcc7dfac4ab0f1f018033c83c02f3ccec50ab9e3233adfa8e91d6b151239b4e6d69d5b7a45adbd774258c4133696415200462dbf41d7a6b5acf0cb56ed8b2cd18b41408119891ab752aad40a0644d7fdfda8bd7b7f79e4b80748530cf8453d8db35b09616a804c249ddc2ca4517ad21a06e374753164a2050bc2a954959671388345db4a67913815053f3260da6432c0a6128d3a014b41228820a0da681427fab4265492660bd610d9bc91e7dc42050d9d4dc9dd3db6b655399a195a8d6aeb92163d8451acc829a2846992df1d92f15a8436d4386d53b6c662d26a51715624157c67eec22a0e063b7991962cfeb10943e1b99216b18199bba6895534ed4c36b770e1adb29bb02af23b02a81bd12df9a32d2c978d23446e319d38a0feb3b8cb89131c78686e35b7659589a36b65a71cb8c670d3d19d733713d9bd5779daa421381aaa67c6ebb075ba2b000166a3007e652e2c504ce2e209b359c1a75f3b51298e6f6e81aaccc7a0147b47c70d818bca4672c9d5e3bb2ddda45f368b08c0e62119c4ee0ac42305921263d8340738fb1d38a1b6963c4c858f154269eccc54bd9d1dceaaf77e707d5689a6943cf309903045617a6c30e82b157115814488adb026776b9aba66358cfea839691657a3a08ac2c48cf289d9e181bfbbd25881aa77e82c0f4dc7ab0e778aeab708e1b43545a21b304e764ca6e8f79de486071005d6293751ac4a181ce966e718ad90badb0d993c6b6187943c0d9c3ab9faf4123cca3427ac5deea4c9bba6d019d858eda561a02335c1870d49cfafde862b915ce842870118ac3de5b93b246fb4c2e8eba4fa7066f81b746e1cd70b1687e09736c4bda5eac89024d076bd218286890c0c220006e0343f25a5b8b0238b031dae93683a330dcb4ae39a1410ab64561182e11570ff59b766a44b45633dd476632d7599fcad806dc6966ed8d4a03d3b69eed045a835a876b72590de6c329b4b9256f12bb462d6344811d68faaceea0b91db5b488066ce75fd9e553d4455d7927ec8ac2a5f0760534b46eaf4d09740374ab1c50e05d4899ef1f42457491d383200e3a6d5dcef7d0141d0aec265021db82101e57c169813dc60dfd3e1aba57810f8890dc6084e0b8572e7b2d46e1c6ff308ddfa5c047c5f941cd4208be2ee0d8d339e806fe380dbc4e81ebc43e61eb4b88dc19b04fd80a7643df4843772af0293cac7816a6101b97e792e08bcb0dfe691a3ca1c0ad01d7e6283b2860c3db0bb50337c20102e7147aae7083dc490f2c33a2500f9f15f7a4f664326b8c8ea295e1f18e1ea678c1caa03b8c6ed7c70cf715bcb1e5623c40a05450a1c0e7a370902ace0de8d2846053111c50ea537593f814aba6c121dbacbe40606990b15b9a90fdea110d462043233c4a20de618e61655ad3d6e46da0c263b80fa02c05be287a0b0a974c886947175a1e5492ddda55f5550dde099753555fc73bc724aac4362a7c930943d3fb1681598230afed3169e87b670694966f7ddfd5e04a783715f77d3caefb8bf3b452e1874c1e1ae68f45575b9a102d9349eb0d666b34896c9b3fd3e0bd700d95f50c81067f59420b159e6592d0687f290fa56cb54c5457f0a1f4baed6f34f8207c88cafadda44329b551e10f4c183af49fe4c521783453b52ef89c977cfaaf1aec858f51492f4c3ae7730d547891e94163ffbb3c76a2b533419dc1c74eb6f77f6970037c922a7a79d2b1135aa8f01f26093784ff12a81624495b02d39408b8c56306795b2044835b601f8a2221962da190883820f67d91e7aac25c7e77e840fb91ede46ee21202737dfa40aca59252dc647d322a649a3848dc6cb9c8181529ef04c18e10dcc55d85651adc03f7d2aea9f01d2f4f0b9554e1915b4ead90997812e8cba68c24dd2f78cbad597304513378178e9bd9b841efc382244c3dabc08b68266eb26d0343d412e8f87f42b8dd508fd7ad0e33336ae9196b939e1ea347f00ec4c0e25e4bc74d4bdfdea7b39379b4d71ccb0e1a9da9b4010d38cdc300f8331b8a817e5b34179f8a40c5e712e1992e8752d0d8efd3b01ce0142074a7c71a717caac5f222fc9cd1320ee52d0b1f84ca96d08350fd00d03f686950c32baee015eb49cb41a8bc1db410fd3cb20f54d27d00d49687a0b67be1fdb41169c07fa3102a0b1f87b082078a22fa05881b26ccc22ca6adf1a77a594cddb97755adf318debcac0c262ebf62efaa967e7c8c6c4631b577333545f482cac3e83ccc46314c7e14a930e25758cc0bed748a940eafa13cdde31001fa2dda4131ddeeb6b99e7cbbaf8ca9bb72a511dfd262df52c5b754f52d8df98788b931da184c4ca53473efe134f3e9c8339a0b9085d2ac6023489b2623fb61da23b0a0bfbc791c163d8673e1d4d5b99775b5c3ceebd3e8ebbabbdd6156a0a8e638cc21385596c052777022f80c702ec659ee93e34c9683aaa63207c254e6b2f02dfced0abfb7b98c38ee75f518fe2c58c9d3a579bade49d2adf60b1871d29de3f7f60650c207201c1241c375f5d56d14b40dda79e64d3c739b9cb9d8c9dce117bbd8c9bcd6e9ea3df9749dee82d9c8739cf35a72287939f253bcc15d4c93a4509d145d7e295427c5b93cc5fafc143dee02ba90a7689753c44a9c1ce7f9e588b97d75014fd2778f98a47e0e06dd88c36027e9e7493a26e178936f0eb7af061c9043b0f95e8985a6d1610b4f33c0d324266349fae6713b6cabc37208d2f7e5e1e04582e7b9999bce4e96671cde46ba73f6b3741fc405f78995ed8358ce310fa0386ffe3227ff28cb1f1e87b1d575875537e6c250dd2178076b52475b08665e09a1e3b03c3267826e3b0afd7b19db1df07ac1b53e4d17087e5e13446bb9476b5f9ed6725fade1eed0d9f575fb61fec2fac9166cdf55217260e259417b1b1473eda5743742ed57101cbd09dc97728fec63828e825b14621f2fe3e64ae84d8573eec331a19c6341382b3c9ced799c150ee7558c3362734638671972527bd8c8a08e0950b320ec81ba9a42bd0ce5a8162f305ced2d5cad15446da5476d5b9eda4a47edfb45b5255c6dcc56bb89897df18462afb5c596a158bcd61422b6eaa462ab1cb11f6162155b6c5412bb7e4300b17b7262f1c253c83ca83ee93ca876c47e82898da9b6da52496ddf792750ab71b5d7e7d4e26588abddcfd55e1a44ed4c8fda8e3cb5331db537895d5bccc556d85d8b267a3ed37bf484bd7bb3adb712f5e23d89ebbd95eb0de47c352775be1a47ef7ea97715493075e30b4e20d8e9e0db728267b987e367b8e0fb83086ef008be2c4f708323f833ce4ee239d2dce1774a735edec5ce6887d51e27e9628fb33f02f5fd8bc7e1ee6e0934c240671e873a74c2f9dce7eb99cfe3358d831ee47bd2750ea884d9286236eec30b858b492f098379a08d0ee8fd32682ceec07c8ebe88c50fabeb9dd88b42b1b883138bdb3c07fb178dc383220fceafe573f068ba4041cd041e82714e70940fd51d9ce061cf509d26322c11862e22eeb2c5f6d025f388b045cde45bef61c1381f85cb84e5fd101ce9b597f70198dec32a3f4e2b231281abe07ab8111670b445a01c87b591e692dca4fc129d94651378ca0ff167200a2c7805af6104be0c5f990af6f2c2d8ddadfc6b12fb1512fb37bcec4f04677fd2c3fea4c8fe6df8ce54b05714c6ee6eefdf93d8af96d87fe065ff5170f6a73cec4f89ec3f819f4e057b6561ecee61e16989fd5a89fde75ef65f04677fcec3fe9cc8fe2bf8f554b05715c6ee9e3d7e2bb1ef91d87fef65ff6370f6e73deccf8bec7f86bf4c057b7561ecee51e6a8c47ebdc47ecccbfeb7e0ec2f79d85f12d9ff01ff9c0af69985b1bb07a37f4bec374becaf78d98f07677fd5c3feaac83e4180b3bfc0d9ef2c84bdc6c36e0463c743d6748a53418a24f8db4478129e7cc1efc603eb4d79f051179e14cbf0f89c83270a5139fc310e7f5721f00d1ef8adc1e01b5cf8a8045f2f8d7cad0b3fa3c7aead05a79feea19feed097629b19243615f48d85d137baf4e512fd4169e82bf3e8ab83d3d778e86b72f445f47fb971fa657838a55fcde47d49cdf5cda6191f10bf7329a35f909b6e80561e60b674e8e6adebe4d6587316363e74a2c6c56ee3393e8d096904fad56ce47f504b0708f119f71edb0b00001e2a0000504b010214001400080808002d72be4e77a5f6923f0000003e0000001400040000000000000000000000000000004d4554412d494e462f4d414e49464553542e4d46feca0000504b010214001400080808002d72be4e4bf3af02770300006c0600001d00000000000000000000000000850000006f72672f776562336a2f61696f6e2f48656c6c6f41766d2e636c617373504b010214001400080808002d72be4e0969797ee00000001e0100002b00000000000000000000000000470400006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249457863657074696f6e2e636c617373504b010214001400080808002d72be4e31a65f2973080000b71700002900000000000000000000000000800500006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249456e636f6465722e636c617373504b010214001400080808002d72be4ef119f71edb0b00001e2a000029000000000000000000000000004a0e00006f72672f61696f6e2f61766d2f757365726c69622f6162692f4142494465636f6465722e636c617373504b05060000000005000500980100007c1a000000000000000505000002bf",
            timestamp = 1560507493830000,
            targetVm = VirtualMachine.AVM,
            nrgPrice = 10000000000.toBigInteger(),
            nrg = 5000000.toBigInteger(),
            nonce = 42.toBigInteger(),
            constructor = true
        )
        assertThat(manager.sign(aionTransaction)).isEqualTo(
            "0xf91cb52a8080b91c3700001c2a504b03041400080808002d72be4e000000000000000000000000140004004d4554412d494e462f4d414e49464553542e4d46feca0000f34dcccb4c4b2d2ed10d4b2d2acecccfb35230d433e0e5f24dccccd375ce492c2eb652c82f4ad72b4f4d32ced24b042ad0f348cdc9c9772ccbe5e5e2e50200504b070877a5f6923f0000003e000000504b03041400080808002d72be4e0000000000000000000000001d0000006f72672f776562336a2f61696f6e2f48656c6c6f41766d2e636c6173738554eb72db44183deb5ca408b54e9c4b2f2925a569eb38100185124828c44e43429da4ad43426aa0ace51d47b12c1969ed4ede810780b70830937a4aa77f99e19d60f85692272431ad7e68f5ed77f67cb7b3faeb9fe72f01dc8160b8e00735eba9a8dcdeb7b8e37bd6aa705d7fa9ddd0c01886f7799b5b2ef76ad666655fd852431fc3b92e664eb9196e1ca31cafedd785b52ee49e5f5de55ed515e174d1f7ebada6864186abaf866ad01906633cc340e3a02403864cf1f8146d385e6d81508b8ee7c8bb0c7dd9996d13e770de403fd20c7ac80fa2041986a2758a32d5916138cfdb0d2beffa76dddee38ea7618c416b129f743d86f1ecd9308a7902170c8ce322e5530b84900c377b217be5a8c5f1998e2b0cd3674a8f7105dfb3b95ce1b6f483030d5769240d5e17f1f68e23f7e82b94dc9321c3cfff8dfc8a5e2f9ccde6ff0e6e1d34450f78b9787af4274a4c280adc754b8e140b26a670cdc05b783b3d88ebca9acc90c26ed00c6a42c69c0c63d91e6d32f106cc21a430c3305a680581f0e45418f9a69c50356f3603165185c754e90df1f424caca1007a1f445db4da4d19dfff6ba8edb0cfd0d9a3a2dd999729ee1ba127e2479258b562802d7a958bce2584bf9b56561fb551168f898a648152c73c94d7c823b4a0a9f9214b2e57c2cbb4503f3f88cae4a353a12377583378489cf9155ce2f748c9cb84a710d1a0aa462f1638bbbe129f5751bfed8c43dac1858c697264694c25358d37199ca8fa36d7a222633518c83ad9bb88c4905dc7c4d89f7bca4c4874427bc13740c13bd345ece9b2861cbc0237cade396895b2a680adfe8983331a72e4a0a65aa2a106d114813dfa99cc7f13d35bd40fcf4eb28496ed7d779738b575cb2cd35cf1341c1e56128a80b46c96f05b65871946f38effb9246cc9b7153435c23fa7e9215019568e88bfe52eaead3fb07b2466865b40ee49e61f857a86748f52d71672227309819ede052d74fed4afca3c9712df71c3781c30440452680c98420fd1b08f22e4034f4d905520312e095846938f73b7211f8fd081cc7d4f1418f9c3e246837a78fe8df1cfb7f421f150dd4ffc0fc6e070bcf70f77eee084bc5d93fa1f5bdd00f6733f923acfe02a383af62fb7e648fe4768fb0f164a783071d6cd3f64e17b61bdb8f4fc1be55a73b78c20ea30e73552706fec6450df31a96d313e909caa542db06b935cad2c025d864a730802aad63e49f26df9bd1fb9de8fddebf504b07084bf3af02770300006c060000504b03041400080808002d72be4e0000000000000000000000002b0000006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249457863657074696f6e2e636c6173734d8e3d4b03411086df399353ce4413c1269d85e007b89d8d12d0a810b13278fd242ec7c86657f6f6827fcb4ab0f007f8a3c48929cc14f3f1ce3b0ff3fdf3f905e01c7dc26188956109def0626e9ada462753c3533157d7e3dbb7997d4dbadc0411062fbc60e3d857e6b1f149e6766dbf41e8af5f9c2dcd849e02855d6963ade2d3f88640f76dac4299f9a5784943c2fed1c33f7e92a2f8eae2b8eca0c07681163a84ee28f83ab14f25bbc6125aa3f0aca5988426ceec9d388b0364ea553006c8a14f6347a70c5bdad112a57957953dd532adf9c9e907baefab67d0fbf3b67f01504b07080969797ee00000001e010000504b03041400080808002d72be4e000000000000000000000000290000006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249456e636f6465722e636c6173739598df771b4715c7ef4a2bed5a7192956c494e223b92d31fb27e4489da2885d4054b8a8b1c3b2948a44d5ca8d7f6da96ab48aeb44ee31692425d28014a43e92f28a4fc38f8151ea02e04c1034fbcf3c039bc7178e13fe09c52716776a59ddd952a350ff6ceccbd773edfb9777e387ffbf80f4d00c8c0cf393851abafa7e472ad9a92af5f4b6d37947aa5bc9c9297cba9996ce17c75a5b6aad405e0389036e5eb72aa2257d753979637951555002707870dab93c48083a1ec95d2f96716668a1738e00a4e9ca7c581fbd172b5ac3ec681333a75791886c0e3011e0e707050a1ce97aa4a764755387045b3538b599ccde8afd52a8a5c254357e99063312b8097035fc724b721d7e51555a913a31c353ad4192c6ed4ea2a19285a0217aaaab2aef914e890c1325fabae93fe39ec7781f6afc5069dadd4641a74967a1e36d6868e08701cadd7c867a986f364cb6a83031ead0bc31081490f84e104ba75e2e56bdbcb152a3faf511a01b521011ec4de55fa5daa11be76ccfcd4dc304c41cc035188b3ab421674a65e977770f9a38bdab2deff89d9beb1a26ca93828408a8330f50d5794eababa11beb6dd50c36b65355cae86d3e1650cdd10e13407fee8bc815a54ebe5eafa392dc30f79e0143c6c9252dc69a8ca35013258253289be52db42ba181b43abad73852e5d0512f811f88c07cec267716a6b891862db85725500acb9a0bd500c53ad5c46cce5620c6b45e3b7168d61a0958ecf543ac6e89c253aad0e6358ab9e514b1918e35a311865a22d3007816eab6ea91bad5b808b1c88eb8a4aaa81d60bda0dc313f0450f5c822fb1fb616675b5ae34d0c61b9d27d5a1b7b5c007981e012e63875aeb14d8303c45e23d095748d96846e1e7e546b8b616deae2a37b6307dcaaa5e4a222cb292d379a64e85e8222dd46148c2490f38e019a459abc8aaaa54d3797ace50fd43c4aea059cab04c2c57f06c59242783c2261ca39b6a834c7095ba3d0ad3c4ad4cddb04e9ee5e008e366ad14e298a38e9f8719e258a38e39019e63cb239d67eb873815a9531672c4699b3a150578de0c69ae2ae256a06e79384fdc5ea46e0501be6e9e8bad26e2344b9dbe0005e2f40dea342bc0cbe6e5660a94f8cc519f59789cf87c9bfacc09f01dac31c6c75497c42b4fbde6e002f1fa3ef5ca0bf0037626adfe749f6074b16bc50ec33c2c90186fe02ee96222c09bac64bdb6f4a03e0c6a29d46128c19749bc77f1fc358f0af0133ce273b56a4395abea65b9b28d95c2e7303076175579e5d90579ab24d363d853ac6dd75794d9724581d3188dc7f39f83a3e006bc0ae1a7d872800813f8136f32ecf919f678f13787bf5db10f60f8b7f4ca18828370481f3e0a4e340738e4ba27cec79d7c29ce1f2dc57fa3db1d0649b79bd3ed829a9d8bd8bd0b02bf07bcf38ee6c0c15dfc1901470b7ce0e004f2137f702dc2459b4ed2c46d022330aac78da30812f7b89bc67593b892f882175a3731aceba8fed106f243a0bba3d0c7310863ba634e773c2b5247913a8e198e92d4fe763301056bc023b8745ac09770fdc97b429586ee890b09a7e42e25f809e9911747e1d8ad57ef9412ae09e954a7e19e90a29d863021853b0d71421aeb3424b4938c161a8a464b9c687fb669c631eb1a4d01e5119a69226f721fee9b8e3b25b23a2146648811196244862c22ef8707f4b0afa048f2e4d8a1221fd8874416958aa834c22a8db04a23acd208ab3462521a31298d989446ac4af104d691540422897c8a8bfd1a46b5ba94d2a854f296f620176b7a6fb57e0507ff08a7aef8d21fc0993fc79aae257e49b3f3a25dacc9140c9ad3af9833ee8e35f7e11c53d31e7006bc1f4192d4f01039a275820f71adc91e7cce46e04382a73f99c0d78bc039ed7e2c146bfe028ec5c763a1878c5db6ebe0769ddc5eebdf0c5a06dc01efc710a53bab85bb9d17e073da36eb6c3ea34bdb80ff454143e4c2d065bcafcbb8659331823236bbcad830848cf415f22011f2b06131ce2f615b6b50518ebdd63f1851878127a2623aed14d2e24dd597767410dad1c168cf5868cf7c2a5abc2075dabfe8b4776db47ea4bddd8d566068fd7d691f27b4278dbd4d694f1afb7bdc45db861a3769336af8bdd687bdd514500ddec29a1aee009e0244cd7f6c6a02a8e6efddd448222327d057ce1d2227651c299a9e9471ac688252c6d1a2294a19c74b7c5ca01d634687483b24a343d27c44a6873ae94dba30e25eab665f98b8be306fe0c2e093464ff35f7159c8f1f84bdbc20471615eeb97e660df75b9100b9dc2c33cc3c7c7fdbc29dbd864938d4d36d77ede9ceadff54ef5022ac2a713936a77d7548f0d94eab1fea98e854ee33d721625057873c6b16d4a38b64df9c6b629ddd836651bdbe664638739d701fed3a51adf837aaad730d1e482bd605b98082e4c26f67b282e2498733e824309762112fa4224f0a649586e9a2170043e82b476d1e09b519f73539f73c636e724ce99c039af2e48e144f3ae9e90a74942a48866374966e2ad3305f14efb1fbea770aa96f6527b92cc4a10be025fd5e77d592feb27c8bc47dcccbca75108bd6733c274acf927f05d7466783f4ff21a4ae26f3f9fde87a522f94e3797a67779bcb7fe950c49de7d589d373d17f9632dbc8f5c022e2c3e12d7f0ad7abc855408b4d6065a878dc1807c7d8036ed40bede40152b50a50d740daa83018df401dab2038df406aa5b81ea6da006be8606021aed0374dd0e34da1be88615e8461b68075e180cc8df07e86b76207f6fa09b56a09b6da05bf84e1f0828d807e89b76a0606fa05d2bd06e1be815f8d66040813e40afda8102bd816e5b816eb781be0bdf1b0c68ac0fd06b76a0b1de40af5b815e6f03dd811f0e0614e903f4233b50a437d05b56a0b7da406fc33b83014df601fab11d68b237d07b56a0f734200ff94f261d684bbfae8bc7ee8917934ec448f2a152d2c55cc14977fb0a1632bc33e3f2bb08d48998df95765232579adc11fad75286df7521db3f9306d30170b49f0be4efc2f7c91ff6e0fa3f504b070831a65f2973080000b7170000504b03041400080808002d72be4e000000000000000000000000290000006f72672f61696f6e2f61766d2f757365726c69622f6162692f4142494465636f6465722e636c617373ad9a0b7c1c451dc7ff93bbcb6e2edbf6f26cda24ed252d36495b425b0a96b69034d7684a9a02498b092adde6b6c995cb6dbd6c4aab88a01451f1d5aa8020d4021690a71652a1147c2be25b514114df8a5a8ae203acc4ffccceeecdec6ddae54c3f4d2f3b3bf3ffffbef3f8cdcc7dfac4ab0f1f018033c83c02f3ccec50ab9e3233adfa8e91d6b151239b4e6d69d5b7a45adbd774258c4133696415200462dbf41d7a6b5acf0cb56ed8b2cd18b41408119891ab752aad40a0644d7fdfda8bd7b7f79e4b80748530cf8453d8db35b09616a804c249ddc2ca4517ad21a06e374753164a2050bc2a954959671388345db4a67913815053f3260da6432c0a6128d3a014b41228820a0da681427fab4265492660bd610d9bc91e7dc42050d9d4dc9dd3db6b655399a195a8d6aeb92163d8451acc829a2846992df1d92f15a8436d4386d53b6c662d26a51715624157c67eec22a0e063b7991962cfeb10943e1b99216b18199bba6895534ed4c36b770e1adb29bb02af23b02a81bd12df9a32d2c978d23446e319d38a0feb3b8cb89131c78686e35b7659589a36b65a71cb8c670d3d19d733713d9bd5779daa421381aaa67c6ebb075ba2b000166a3007e652e2c504ce2e209b359c1a75f3b51298e6f6e81aaccc7a0147b47c70d818bca4672c9d5e3bb2ddda45f368b08c0e62119c4ee0ac42305921263d8340738fb1d38a1b6963c4c858f154269eccc54bd9d1dceaaf77e707d5689a6943cf309903045617a6c30e82b157115814488adb026776b9aba66358cfea839691657a3a08ac2c48cf289d9e181bfbbd25881aa77e82c0f4dc7ab0e778aeab708e1b43545a21b304e764ca6e8f79de486071005d6293751ac4a181ce966e718ad90badb0d993c6b6187943c0d9c3ab9faf4123cca3427ac5deea4c9bba6d019d858eda561a02335c1870d49cfafde862b915ce842870118ac3de5b93b246fb4c2e8eba4fa7066f81b746e1cd70b1687e09736c4bda5eac89024d076bd218286890c0c220006e0343f25a5b8b0238b031dae93683a330dcb4ae39a1410ab64561182e11570ff59b766a44b45633dd476632d7599fcad806dc6966ed8d4a03d3b69eed045a835a876b72590de6c329b4b9256f12bb462d6344811d68faaceea0b91db5b488066ce75fd9e553d4455d7927ec8ac2a5f0760534b46eaf4d09740374ab1c50e05d4899ef1f42457491d383200e3a6d5dcef7d0141d0aec265021db82101e57c169813dc60dfd3e1aba57810f8890dc6084e0b8572e7b2d46e1c6ff308ddfa5c047c5f941cd4208be2ee0d8d339e806fe380dbc4e81ebc43e61eb4b88dc19b04fd80a7643df4843772af0293cac7816a6101b97e792e08bcb0dfe691a3ca1c0ad01d7e6283b2860c3db0bb50337c20102e7147aae7083dc490f2c33a2500f9f15f7a4f664326b8c8ea295e1f18e1ea678c1caa03b8c6ed7c70cf715bcb1e5623c40a05450a1c0e7a370902ace0de8d2846053111c50ea537593f814aba6c121dbacbe40606990b15b9a90fdea110d462043233c4a20de618e61655ad3d6e46da0c263b80fa02c05be287a0b0a974c886947175a1e5492ddda55f5550dde099753555fc73bc724aac4362a7c930943d3fb1681598230afed3169e87b670694966f7ddfd5e04a783715f77d3caefb8bf3b452e1874c1e1ae68f45575b9a102d9349eb0d666b34896c9b3fd3e0bd700d95f50c81067f59420b159e6592d0687f290fa56cb54c5457f0a1f4baed6f34f8207c88cafadda44329b551e10f4c183af49fe4c521783453b52ef89c977cfaaf1aec858f51492f4c3ae7730d547891e94163ffbb3c76a2b533419dc1c74eb6f77f6970037c922a7a79d2b1135aa8f01f26093784ff12a81624495b02d39408b8c56306795b2044835b601f8a2221962da190883820f67d91e7aac25c7e77e840fb91ede46ee21202737dfa40aca59252dc647d322a649a3848dc6cb9c8181529ef04c18e10dcc55d85651adc03f7d2aea9f01d2f4f0b9554e1915b4ead90997812e8cba68c24dd2f78cbad597304513378178e9bd9b841efc382244c3dabc08b68266eb26d0343d412e8f87f42b8dd508fd7ad0e33336ae9196b939e1ea347f00ec4c0e25e4bc74d4bdfdea7b39379b4d71ccb0e1a9da9b4010d38cdc300f8331b8a817e5b34179f8a40c5e712e1992e8752d0d8efd3b01ce0142074a7c71a717caac5f222fc9cd1320ee52d0b1f84ca96d08350fd00d03f686950c32baee015eb49cb41a8bc1db410fd3cb20f54d27d00d49687a0b67be1fdb41169c07fa3102a0b1f87b082078a22fa05881b26ccc22ca6adf1a77a594cddb97755adf318debcac0c262ebf62efaa967e7c8c6c4631b577333545f482cac3e83ccc46314c7e14a930e25758cc0bed748a940eafa13cdde31001fa2dda4131ddeeb6b99e7cbbaf8ca9bb72a511dfd262df52c5b754f52d8df98788b931da184c4ca53473efe134f3e9c8339a0b9085d2ac6023489b2623fb61da23b0a0bfbc791c163d8673e1d4d5b99775b5c3ceebd3e8ebbabbdd6156a0a8e638cc21385596c052777022f80c702ec659ee93e34c9683aaa63207c254e6b2f02dfced0abfb7b98c38ee75f518fe2c58c9d3a579bade49d2adf60b1871d29de3f7f60650c207201c1241c375f5d56d14b40dda79e64d3c739b9cb9d8c9dce117bbd8c9bcd6e9ea3df9749dee82d9c8739cf35a72287939f253bcc15d4c93a4509d145d7e295427c5b93cc5fafc143dee02ba90a7689753c44a9c1ce7f9e588b97d75014fd2778f98a47e0e06dd88c36027e9e7493a26e178936f0eb7af061c9043b0f95e8985a6d1610b4f33c0d324266349fae6713b6cabc37208d2f7e5e1e04582e7b9999bce4e96671cde46ba73f6b3741fc405f78995ed8358ce310fa0386ffe3227ff28cb1f1e87b1d575875537e6c250dd2178076b52475b08665e09a1e3b03c3267826e3b0afd7b19db1df07ac1b53e4d17087e5e13446bb9476b5f9ed6725fade1eed0d9f575fb61fec2fac9166cdf55217260e259417b1b1473eda5743742ed57101cbd09dc97728fec63828e825b14621f2fe3e64ae84d8573eec331a19c6341382b3c9ced799c150ee7558c3362734638671972527bd8c8a08e0950b320ec81ba9a42bd0ce5a8162f305ced2d5cad15446da5476d5b9eda4a47edfb45b5255c6dcc56bb89897df18462afb5c596a158bcd61422b6eaa462ab1cb11f6162155b6c5412bb7e4300b17b7262f1c253c83ca83ee93ca876c47e82898da9b6da52496ddf792750ab71b5d7e7d4e26588abddcfd55e1a44ed4c8fda8e3cb5331db537895d5bccc556d85d8b267a3ed37bf484bd7bb3adb712f5e23d89ebbd95eb0de47c352775be1a47ef7ea97715493075e30b4e20d8e9e0db728267b987e367b8e0fb83086ef008be2c4f708323f833ce4ee239d2dce1774a735edec5ce6887d51e27e9628fb33f02f5fd8bc7e1ee6e0934c240671e873a74c2f9dce7eb99cfe3358d831ee47bd2750ea884d9286236eec30b858b492f098379a08d0ee8fd32682ceec07c8ebe88c50fabeb9dd88b42b1b883138bdb3c07fb178dc383220fceafe573f068ba4041cd041e82714e70940fd51d9ce061cf509d26322c11862e22eeb2c5f6d025f388b045cde45bef61c1381f85cb84e5fd101ce9b597f70198dec32a3f4e2b231281abe07ab8111670b445a01c87b591e692dca4fc129d94651378ca0ff167200a2c7805af6104be0c5f990af6f2c2d8ddadfc6b12fb1512fb37bcec4f04677fd2c3fea4c8fe6df8ce54b05714c6ee6eefdf93d8af96d87fe065ff5170f6a73cec4f89ec3f819f4e057b6561ecee61e16989fd5a89fde75ef65f04677fcec3fe9cc8fe2bf8f554b05715c6ee9e3d7e2bb1ef91d87fef65ff6370f6e73deccf8bec7f86bf4c057b7561ecee51e6a8c47ebdc47ecccbfeb7e0ec2f79d85f12d9ff01ff9c0af69985b1bb07a37f4bec374becaf78d98f07677fd5c3feaac83e4180b3bfc0d9ef2c84bdc6c36e0463c743d6748a53418a24f8db4478129e7cc1efc603eb4d79f051179e14cbf0f89c83270a5139fc310e7f5721f00d1ef8adc1e01b5cf8a8045f2f8d7cad0b3fa3c7aead05a79feea19feed097629b19243615f48d85d137baf4e512fd4169e82bf3e8ab83d3d778e86b72f445f47fb971fa657838a55fcde47d49cdf5cda6191f10bf7329a35f909b6e80561e60b674e8e6adebe4d6587316363e74a2c6c56ee3393e8d096904fad56ce47f504b0708f119f71edb0b00001e2a0000504b010214001400080808002d72be4e77a5f6923f0000003e0000001400040000000000000000000000000000004d4554412d494e462f4d414e49464553542e4d46feca0000504b010214001400080808002d72be4e4bf3af02770300006c0600001d00000000000000000000000000850000006f72672f776562336a2f61696f6e2f48656c6c6f41766d2e636c617373504b010214001400080808002d72be4e0969797ee00000001e0100002b00000000000000000000000000470400006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249457863657074696f6e2e636c617373504b010214001400080808002d72be4e31a65f2973080000b71700002900000000000000000000000000800500006f72672f61696f6e2f61766d2f757365726c69622f6162692f414249456e636f6465722e636c617373504b010214001400080808002d72be4ef119f71edb0b00001e2a000029000000000000000000000000004a0e00006f72672f61696f6e2f61766d2f757365726c69622f6162692f4142494465636f6465722e636c617373504b05060000000005000500980100007c1a000000000000000505000002bf87058b45f6d6cd70834c4b408800000002540be40002b86008fe2bf5757b8261d4937f13b5815448f2144f9c1409a3fab4c99ca86fff8a36a91c55533ae0493dcae07cc47c412a59d8e9b2bf02d12ead1d20ebd2386996fa9a29c8753ae82013b85a8453da3688e4cd436d3f71d4dc450e1d5b6504940804"
        )
    }

    @Test
    fun `sign raw transaction call AVM`() {
        val aionTransaction = AionTransaction(
            to = "0xa01af11dc05cc9aedcafdc80c8b94301e11540cf7fbdcff477b6bd964a208adc",
            data = "0x210009736574537472696e6721000a48656c6c6f2074657374",
            timestamp = 1560788090116000,
            targetVm = VirtualMachine.AVM,
            nrgPrice = 10000000000.toBigInteger(),
            nrg = 2000000.toBigInteger(),
            nonce = 80.toBigInteger()
        )
        assertThat(manager.sign(aionTransaction)).isEqualTo(
            "0xf8b550a0a01af11dc05cc9aedcafdc80c8b94301e11540cf7fbdcff477b6bd964a208adc8099210009736574537472696e6721000a48656c6c6f207465737487058b874bae57a0831e84808800000002540be40001b86008fe2bf5757b8261d4937f13b5815448f2144f9c1409a3fab4c99ca86fff8a36d0e612390be454382a7556b0419214f063f220b874863715f5ca121da7685bca64623d8b730ec273cc13687530a977975f729e0a8b172131c333f3d8f0905e02"
        )
    }

    @Test
    fun `sign raw transaction constructor FVM`() {
        val aionTransaction = AionTransaction(
            data = "0x605060405234156100105760006000fd5b604051610469380380610469833981016040528080518201919060100150505b5b3360006000508282909180600101839055555050505b8060026000509080519060100190610060929190610068565b505b5061011a565b8280546001816001161561010002031660029004906000526010600020905090600f016010900481019282600f106100ab57805160ff19168380011785556100de565b828001600101855582156100de579182015b828111156100dd57825182600050909055916010019190600101906100bd565b5b5090506100eb91906100ef565b5090565b61011791906100f9565b8082111561011357600081815060009055506001016100f9565b5090565b90565b610340806101296000396000f30060506040526000356c01000000000000000000000000900463ffffffff16806341c0e1b5146100495780634ac0d66e1461005f578063cfae3217146100c157610043565b60006000fd5b34156100555760006000fd5b61005d610151565b005b341561006b5760006000fd5b6100bf6004808035906010019082018035906010019191908080600f01601080910402601001604051908101604052809392919081815260100183838082843782019150505050505090909190505061017b565b005b34156100cd5760006000fd5b6100d5610199565b6040518080601001828103825283818151815260100191508051906010019080838360005b838110156101165780820151818401525b6010810190506100fa565b50505050905090810190600f1680156101435780820380516001836010036101000a031916815260100191505b509250505060405180910390f35b60006000508060010154905433909114919014161561017857600060005080600101549054ff5b5b565b806002600050908051906010019061019492919061024b565b505b50565b6101a16102d2565b60026000508054600181600116156101000203166002900480600f01601080910402601001604051908101604052809291908181526010018280546001816001161561010002031660029004801561023c5780600f1061020f5761010080835404028352916010019161023c565b8201919060005260106000209050905b81548152906001019060100180831161021f57829003600f168201915b50505050509050610248565b90565b8280546001816001161561010002031660029004906000526010600020905090600f016010900481019282600f1061028e57805160ff19168380011785556102c1565b828001600101855582156102c1579182015b828111156102c057825182600050909055916010019190600101906102a0565b5b5090506102ce91906102e9565b5090565b601060405190810160405280600081526010015090565b61031191906102f3565b8082111561030d57600081815060009055506001016102f3565b5090565b905600a165627a7a72305820123c90d8d490b8d315d013e25883bfa13527def3322565fdb8c0b6fd4fae93710029000000000000000000000000000000100000000000000000000000000000000941696f6e20746573740000000000000000000000000000000000000000000000",
            timestamp = 1560357094035000,
            targetVm = VirtualMachine.FVM,
            nrgPrice = 10000000000.toBigInteger(),
            nrg = 4000000.toBigInteger(),
            nonce = 25.toBigInteger(),
            constructor = true
        )
        assertThat(manager.sign(aionTransaction)).isEqualTo(
            "0xf90527198080b904a9605060405234156100105760006000fd5b604051610469380380610469833981016040528080518201919060100150505b5b3360006000508282909180600101839055555050505b8060026000509080519060100190610060929190610068565b505b5061011a565b8280546001816001161561010002031660029004906000526010600020905090600f016010900481019282600f106100ab57805160ff19168380011785556100de565b828001600101855582156100de579182015b828111156100dd57825182600050909055916010019190600101906100bd565b5b5090506100eb91906100ef565b5090565b61011791906100f9565b8082111561011357600081815060009055506001016100f9565b5090565b90565b610340806101296000396000f30060506040526000356c01000000000000000000000000900463ffffffff16806341c0e1b5146100495780634ac0d66e1461005f578063cfae3217146100c157610043565b60006000fd5b34156100555760006000fd5b61005d610151565b005b341561006b5760006000fd5b6100bf6004808035906010019082018035906010019191908080600f01601080910402601001604051908101604052809392919081815260100183838082843782019150505050505090909190505061017b565b005b34156100cd5760006000fd5b6100d5610199565b6040518080601001828103825283818151815260100191508051906010019080838360005b838110156101165780820151818401525b6010810190506100fa565b50505050905090810190600f1680156101435780820380516001836010036101000a031916815260100191505b509250505060405180910390f35b60006000508060010154905433909114919014161561017857600060005080600101549054ff5b5b565b806002600050908051906010019061019492919061024b565b505b50565b6101a16102d2565b60026000508054600181600116156101000203166002900480600f01601080910402601001604051908101604052809291908181526010018280546001816001161561010002031660029004801561023c5780600f1061020f5761010080835404028352916010019161023c565b8201919060005260106000209050905b81548152906001019060100180831161021f57829003600f168201915b50505050509050610248565b90565b8280546001816001161561010002031660029004906000526010600020905090600f016010900481019282600f1061028e57805160ff19168380011785556102c1565b828001600101855582156102c1579182015b828111156102c057825182600050909055916010019190600101906102a0565b5b5090506102ce91906102e9565b5090565b601060405190810160405280600081526010015090565b61031191906102f3565b8082111561030d57600081815060009055506001016102f3565b5090565b905600a165627a7a72305820123c90d8d490b8d315d013e25883bfa13527def3322565fdb8c0b6fd4fae93710029000000000000000000000000000000100000000000000000000000000000000941696f6e2074657374000000000000000000000000000000000000000000000087058b22f2500e38833d09008800000002540be40001b86008fe2bf5757b8261d4937f13b5815448f2144f9c1409a3fab4c99ca86fff8a36e7714f6dc1a449cd77574240f2fda38474eeabd68aae0c0418b1564a0416eece7dc9d6552e7ec37ee8a3c4df6605ccde0396512a69b2edeeed8c97c36abdb80a"
        )
    }

    @Test
    fun `sign raw transaction call FVM`() {
        val aionTransaction = AionTransaction(
            data = "0x4ac0d66e000000000000000000000000000000100000000000000000000000000000000e48657920796f7520616761696e21000000000000000000000000000000000000",
            to = "0xa0db10f717b33fc4117f7e82a6b8cc575aa130411ab00591a990971e8a1d1bb6",
            timestamp = 1560447038176000,
            targetVm = VirtualMachine.FVM,
            nrgPrice = 10000000000.toBigInteger(),
            nrg = 54321.toBigInteger(),
            nonce = 35.toBigInteger()
        )
        assertThat(manager.sign(aionTransaction)).isEqualTo(
            "0xf8e023a0a0db10f717b33fc4117f7e82a6b8cc575aa130411ab00591a990971e8a1d1bb680b8444ac0d66e000000000000000000000000000000100000000000000000000000000000000e48657920796f7520616761696e2100000000000000000000000000000000000087058b37e366bb0082d4318800000002540be40001b86008fe2bf5757b8261d4937f13b5815448f2144f9c1409a3fab4c99ca86fff8a3678d0e91ee92387877a1a7df20fc65028000ab4d26676104756064405825d75b83294f64d7b2babb88c5786371ce3409bfc85dc6baf4fd31a697220c3c806870f"
        )
    }

    companion object {
        const val PRIVATE_KEY =
            "0x4776895c43f77676cdec51a6c92d2a1bacdf16ddcc6e7e07ab39104b42e1e52608fe2bf5757b8261d4937f13b5815448f2144f9c1409a3fab4c99ca86fff8a36"
    }
}
