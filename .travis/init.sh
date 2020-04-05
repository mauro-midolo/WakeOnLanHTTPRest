#!/bin/sh
MyAccess=${MYACCESS}
MyKey=${MYKEY}
mkdir ~/.ssh
echo "${MyAccess}" | base64 --decode > ~/.ssh/id_rsa
chmod 400 ~/.ssh/id_rsa
echo "${MyKey}" | base64 --decode > mykey.key
chmod 400 mykey.key
gpg --import mykey.key

