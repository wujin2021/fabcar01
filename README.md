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
* Golang
* Hyperledger Fabric

下面将指导您完成此过程：

**JDK1.8**

项目网站: https://www.oracle.com/technetwork/java/javase/downloads

```
下载安装JDK1.8并配置系统环境变量
```

**JDK1.8**

项目网站: https://dev.mysql.com/downloads/mysql/

```
下载安装MySQL8.0.12并配置相关文件
```

**Docker 和 Docker Compose**

项目网站: https://docs.docker.com/install/linux/docker-ce/ubuntu/ (Docker)\
项目网站: https://docs.docker.com/compose/install/ (Docker Compose)

```
sudo apt-get install docker-ce docker-ce-cli containerd.io
sudo apt-get install docker-compose
```

**Node.js, NPM 和 NVM**

项目网站: https://nodejs.org/en/ (Node.js)\
项目网站: https://www.npmjs.com/ (NPM)\
项目仓库: https://github.com/creationix/nvm#installation-and-update (NVM)

```
sudo apt-get install nodejs
nvm install 12.5.0
nvm use 12.5.0
```

**Golang**

项目网站: https://go.dev/dl/

```
sudo apt install wget
wget https://go.dev/dl/go1.19.2.linux-amd64.tar.gz
sudo tar -zxvf go1.19.2.linux-amd64.tar.gz -C /usr/local/
sudo vim ~/.bashrc
在.bashrc中添加以下语句配置环境变量：
  export GOROOT =/usr/local/go
  export GOPATH=$HOME/go
  export PATH=$GOROOT/bin:$PATH
source ~/.hashrc
go version
```

**Hyperledger Fabric**

项目网站: https://www.hyperledger.org/projects/fabric

```
git clone https://github.com/hyperledger/fabric-samples
cd fabric-samples
./scripts/bootstrap.sh 1.1.0 1.1.0
cd first-network
./byfn.sh generate
./byfn.sh up
```

## 配置文件修改

**配置connection.json文件**

自行更换部署链码的ip，以及各个证书和私钥路径。

```
"url": "grpc://10.10.9.128:7050"
"adminPrivateKeyPEM": {
        "path": "src/main/resources/crypto-config/ordererOrganizations/example.com/users/Admin@example.com/msp/keystore/1deeab5433fa6e5f045eb763109d6165268fba153211af1281f00d45f54b1022_sk"
      }
```

**配置fabric.config.properties文件**
```
# 网络配置文件路径
networkConfigPath = src/main/resources/connection.json
# 用户证书路径
certificatePath = src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/signcerts/User1@org1.example.com-cert.pem
# 用户私钥路径
privateKeyPath = src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/keystore/priv_sk
# 通道名字
channelName = mychannel
# 链码名字
contractName = fabcar
```
**配置相关生成文件**

把/go/src/github.com/hyperledger/fabric/fabric-samples/test-network/organizations下的生成的两个文件夹ordererOrganizations.example.com 和 peerOrganizations 下载下来，更换在idea项目目录resources下crypto-config文件夹下的文件

**配置链码文件**

将~/go/src/github.com/hyperledger/fabric-samples/chaincode/fabcar下的链码文件fabcar.go更换为本项目目录下的fabcar.go文件

## 运行程序
Running the application is very simple. You just need to:

- 运行Hyperledger Fabric
```
cd ~/go/src/github.com/hyperledger/fabric-samples/fabcar
docker rm -f | $(docker ps -aq)
./startFabric.sh
```

- 运行前端界面
```
npm start
```


---


