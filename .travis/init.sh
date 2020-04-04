#!/bin/sh
Passphrase=${PASSPHRASE}
NameReal=${MYNAME}
NameEmail=${MYEMAIL}
MyAccess=${MYACCESS}
cat > keydetails <<EOF
    %echo Generating a basic OpenPGP key
    Key-Type: RSA
    Key-Length: 2048
    Subkey-Type: RSA
    Subkey-Length: 2048
    Name-Real: ${NameReal}
    Name-Email: ${NameEmail}
    Passphrase: ${Passphrase}
    Expire-Date: 365
    %commit
    %echo done
EOF
mkdir ~/.ssh
echo "${MyAccess}" | base64 --decode > ~/.ssh/id_rsa
chmod 400 ~/.ssh/id_rsa
gpg --verbose --batch --gen-key keydetails > /dev/null 2>&1
gpg --keyserver keyserver.ubuntu.com --send-keys

