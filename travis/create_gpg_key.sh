#!/bin/sh
Passphrase=${PASSPHRASE}
NameReal=${MYNAME}
NameEmail=${MYEMAIL}
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

gpg --verbose --batch --gen-key keydetails

gpg --keyserver keyserver.ubuntu.com --send-keys

