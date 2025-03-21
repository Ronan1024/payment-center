#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "执行异常 版本号 与 应用名称不能为空"
    echo "使用方式: ./run VERSION APP_NAME"
    echo "例如: ./run 1.0.0 app"
    exit 1
fi

VERSION=$1
APP_NAME=$2
JAR=${APP_NAME}-${VERSION}.jar

echo "================================================="
echo "             开始 构建${JAR} 镜像"
echo "================================================="
if [ ! -f "./$APP_NAME/$JAR" ]; then
  if [ ! -f "/home/baoadmin/$JAR" ]; then
    echo "当前: ${JAR} 不存在"
    exit 1
  fi
  mv /home/baoadmin/${APP_NAME}-${VERSION}.jar ./${APP_NAME}
fi

rm -f ./${APP_NAME}/${APP_NAME}.jar

cp ./${APP_NAME}/${APP_NAME}-${VERSION}.jar ./${APP_NAME}/${APP_NAME}.jar

cd ./${APP_NAME}

docker build  -t ${APP_NAME}:${VERSION} .

if [ $? -eq 0 ]; then
    echo "构建成功: ${APP_NAME}:${VERSION}"
else
    echo "构建失败"
    exit 1
fi

echo "================================================="
echo "             开始 停止 ${APP_NAME} 镜像"
echo "================================================="
docker rm -f ${APP_NAME}


echo "================================================="
echo "             开始 启动Docker 镜像"
echo "================================================="

JAVA_VERSION=${VERSION} docker-compose up -d ${APP_NAME}

docker-compose logs -f --tail=200 ${APP_NAME}

cd ..