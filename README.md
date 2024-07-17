# 云际要素区块链安全获取与融合系统 

面向云际环境下大规模资源跨域协同交互的可信存证与监管需求，充分考虑大规模资源跨域协同交互场景下多方计算的数据隐私性和安全性要求高等特点，云际要素区块链安全获取与融合系统的主要功能包括：为异构多源的云际要素数据分别设计相应的业务，然后，对不同业务中的云际要素数据进行管理，包括对云际要素数据进行摘要提取，使用链上链下智能协同技术对数据进行存证，使用同态加密技术实现对存储数据的加密和密文查询等，另外实现了对区块链的可视化，能够展示区块链的网络、链码、区块、事务等信息。

## 安装与使用

为了使用该系统，必须下载并安装以下依赖项：
* JDK1.8
* MySQL
* Docker
* Docker Compose
* Node.js
* NPM
* NVM
* Hyperledger Fabric

Here we guide you through this process.

**Interplanetary File System (IPFS)**

Project website: https://ipfs.io/

```
wget https://dist.ipfs.io/go-ipfs/v0.4.19/go-ipfs_v0.4.19_linux-amd64.tar.gz
tar xvfz go-ipfs_v0.4.19_linux-amd64.tar.gz
cd go-ipfs
sudo ./install.sh
ipfs init
```

**Docker and Docker Compose**

Project website: https://docs.docker.com/install/linux/docker-ce/ubuntu/ (Docker)\
Project website: https://docs.docker.com/compose/install/ (Docker Compose)

```
sudo apt-get install docker-ce docker-ce-cli containerd.io
sudo apt-get install docker-compose
```

**Node.js, NPM and NVM**

Project website: https://nodejs.org/en/ (Node.js)\
Project website: https://www.npmjs.com/ (NPM)\
Project repo: https://github.com/creationix/nvm#installation-and-update (NVM)

```
sudo apt-get install nodejs
nvm install 8.15.0
nvm use 8.15.0
```

**Hyperledger Fabric**

Project website: https://www.hyperledger.org/projects/fabric

```
git clone https://github.com/hyperledger/fabric-samples
cd fabric-samples
./scripts/bootstrap.sh 1.4.0 1.4.0
cd first-network
./byfn.sh generate
./byfn.sh up
```

**Download Airmed Foundation**

Project website: https://airmedfoundation.thechain.tech/

```
git clone https://github.com/the-chain/airmedfoundation-terminal
cd airmedfoundation-terminal
npm install 
```

**Install Chaincode to Hyperledger Network**

```
cd airmedfoundation-terminal
docker exec -it cli peer chaincode instantiate -C mychannel -l "node" -n airmed -v v1 -c '{"Args":[]}' -o orderer.example.com:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
docker exec -it cli peer chaincode instantiate -C mychannel -l "node" -n secureRec -v v1 -c '{"Args":[]}' -o orderer.example.com:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
```

## Configuration of Airmed Foundation's server

**Edit config/datastores.js**

Change values to connect to postgreSQL. In order to install postgreSQL, you can run the following command in the terminal:
```
docker-compose up -d
```

**Setup appconfig.json**
```
{ "email": { "admin":"admin_email@gmail.com", "auth": { "user": "admin_email@gmail.com", "pass": "password" }, "service": "Gmail", "emailVerification": 1 }, "database": { "adapter": "sails-postgresql", "user": "admin", "password": "adminpassword", "port": "5432", "host": "localhost", "database": "airmed" }, "session": { "name": "sails.sid", "secret": "secretKey", "redis": { "url": "redis://: redisPassword@127.0.0.1.com:6379/15", "host": "127.0.0.1", "port": 6379, "pass": "redisPassword", "db": 15 } }, "ipfs": { "host":"127.0.0.1", "port":"5001" } }
```

**Edit config/policies.js**

For testing purposes, remove comment from line 20. 
That is, line 
```
//'*': 'isHTTPS',
```
should now look like
```
'*': 'isHTTPS',
```

**Edit Hyperledger configuration file**

In the file fabric-api/config/configfile.yaml, you should replace the default admin certificate paths with the actual path of your project. Please note that you should only modify paths from adminPrivateKey in Line 44 under Org1, and in Line 56 under Org2. Everything else must remain unaltered.

As a further advice, please make sure that fabric-sample and airmedfoundation-terminal are in the same directory. 

The paths should look as follows:\
/home/root/Desktop/fabric-sample\
/home/root/Desktop/airmedfoundation-terminal

## Running the application
Running the application is very simple. You just need to:

- Run IPFS
```
ipfs daemon &
```

- Run the server
```
node ./node_modules/sails/bin/sails.js l --redis --safe
```

- Run the sync process
```
./init.sh &
```

And now you're ready to go!

---

**Contact:** This open source project is brought to you by [The Chain](http://thechain.tech/), a software shop specialized in the development of applications and services based on blockchain technology and artificial intelligence. Any questions that might arise should be sent to the project's maintainers. We will be happy to assist you.

#### E-mail: admin@thechain.tech

**License:** This project is under the GNU Affero General Public License v3.0. 

<a href="https://airmedfoundation.thechain.tech/"><img src="https://media.licdn.com/dms/image/C4E0BAQGs_7h67j1y0w/company-logo_400_400/0?e=1574899200&v=beta&t=KLfoiPbZSGZvBHmqhxCTYC211phfpr46j4pedsZMJ8I" width="200" height="200" /></a>


