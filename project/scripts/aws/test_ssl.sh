#!/usr/bin/env bash


# localhost = 127.0.0.1

openssl s_client -connect 127.0.0.1:4433 -cert client1.crt -key client1.key -CAfile ca.crt -tls1 "$@"




# No nhai lai phong cach cua thang cu lin nay - a du - tao bat ngo day - may ko co dao duc nghe nghiep - no gia do do ma


