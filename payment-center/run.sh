#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "执行异常 应用名称 与 版本号不能为空"
    echo "使用方式: ./run APP_NAME VERSION"
    echo "例如: ./run app 1.0.0"
    exit 1
fi

VERSION=$2
APP_NAME=$1

echo $(pwd)

WORK_DIR=$(pwd)

docker rm -f ${APP_NAME}

docker run -itd --name ${APP_NAME}  -p 27001:27001 -e NACOS_HOST=10.125.123.8 -e NACOS_PORT=8848 -e NACOS_NAMESPACE=0dd5b663-0488-4b49-9a13-df247e5f8c61 -e TZ=Asia/Shanghai -v ${WORK_DIR}/${APP_NAME}:/opt/projects/saas/log ${APP_NAME}:${VERSION}