#!/bin/bash

# 参数说明
# $1 jarName 默认 ifast-1.0.0.jar
# $2 branch 默认 dev
# $3 appDir 默认 /home/server/webapps/ifast
# $4 profile 激活的配置文件，默认dev

#
# step 1.
#

# $1
# jar名称默认
jarName=$1
if [ -z "${jarName}" ]
then
    jarName=ifast-1.0.0.jar
fi
echo "jarName: ${jarName}"

# $2
# 将指定分支最新代码部署到指定目录。默认dev
# 如果目录不存在会自动创建。

branch=$2

if [ -z "${branch}" ]
then
    branch=dev
fi
echo "branch: ${branch}"

# $3
appDir=$3
if [ -z "${appDir}" ]
then
    appDir=/home/server/webapps/ifast
fi

if [ ! -d ${appDir} ]
then
    echo "目录 ${appDir} 不存在，尝试创建 ..."
    mkdir -p ${appDir}
fi

echo "appDir: ${appDir}"

# $4
profile=$4
if [ -z "${profile}" ]
then
    profile=dev
fi
echo "profile: ${profile}"

#
# step 2.
#

srcDir=`pwd`

git pull origin ${branch}

mvn install

#
# step 3. shutdown > delete | backup
#

cd ${appDir}
echo "shutdown ..."
if [ -d "bin" ]
then
    cd bin
    ./shutdown.sh ${jarName}
    ps -ef | grep ${jarName}
    cd ..
fi


echo "del ${appDir}/${jarName}"
rm -f ${jarName}
echo "del ${appDir}/bin"
rm -rf bin

# move | copy > chmod
echo "move ${srcDir}/target/${jarName} to ${appDir}"
mv ${srcDir}/target/${jarName} ${appDir}

echo "copy dir ${srcDir}/bin to `pwd`"
cp -R ${srcDir}/bin .
chmod u+x ${appDir}/bin/*

#
# step 4. start
#

echo "startup ..."
cd bin
./startup.sh ${jarName} ${profile}