                        _                     _ _ _   
                        | |__   __ _ _ __   __| (_) |_ 
                        | '_ \ / _` | '_ \ / _` | | __|
                        | |_) | (_| | | | | (_| | | |_ 
                        |_.__/ \__,_|_| |_|\__,_|_|\__|
                                                       

                      This is an OverTheWire game server. 
            More information on http://www.overthewire.org/wargames

#The password for the next level can be retrieved by submitting the password of the current level to port 30001 on localhost using SSL/TLS encryption.
Helpful note: Getting “DONE”, “RENEGOTIATING” or “KEYUPDATE”? Read the “CONNECTED COMMANDS” section in the manpage.

```
bandit15@bandit:~$ openssl s_client -connect localhost:30001
CONNECTED(00000003)
Can't use SSL_get_servername
depth=0 CN = SnakeOil
verify error:num=18:self-signed certificate
verify return:1
depth=0 CN = SnakeOil
verify return:1
---
Certificate chain
 0 s:CN = SnakeOil
   i:CN = SnakeOil
   a:PKEY: rsaEncryption, 4096 (bit); sigalg: RSA-SHA256
   v:NotBefore: Jun 10 03:59:50 2024 GMT; NotAfter: Jun  8 03:59:50 2034 GMT
---
Server certificate
-----BEGIN CERTIFICATE-----
MIIFBzCCAu+gAwIBAgIUBLz7DBxA0IfojaL/WaJzE6Sbz7cwDQYJKoZIhvcNAQEL
BQAwEzERMA8GA1UEAwwIU25ha2VPaWwwHhcNMjQwNjEwMDM1OTUwWhcNMzQwNjA4
MDM1OTUwWjATMREwDwYDVQQDDAhTbmFrZU9pbDCCAiIwDQYJKoZIhvcNAQEBBQAD
ggIPADCCAgoCggIBANI+P5QXm9Bj21FIPsQqbqZRb5XmSZZJYaam7EIJ16Fxedf+
jXAv4d/FVqiEM4BuSNsNMeBMx2Gq0lAfN33h+RMTjRoMb8yBsZsC063MLfXCk4p+
09gtGP7BS6Iy5XdmfY/fPHvA3JDEScdlDDmd6Lsbdwhv93Q8M6POVO9sv4HuS4t/
jEjr+NhE+Bjr/wDbyg7GL71BP1WPZpQnRE4OzoSrt5+bZVLvODWUFwinB0fLaGRk
GmI0r5EUOUd7HpYyoIQbiNlePGfPpHRKnmdXTTEZEoxeWWAaM1VhPGqfrB/Pnca+
vAJX7iBOb3kHinmfVOScsG/YAUR94wSELeY+UlEWJaELVUntrJ5HeRDiTChiVQ++
wnnjNbepaW6shopybUF3XXfhIb4NvwLWpvoKFXVtcVjlOujF0snVvpE+MRT0wacy
tHtjZs7Ao7GYxDz6H8AdBLKJW67uQon37a4MI260ADFMS+2vEAbNSFP+f6ii5mrB
18cY64ZaF6oU8bjGK7BArDx56bRc3WFyuBIGWAFHEuB948BcshXY7baf5jjzPmgz
mq1zdRthQB31MOM2ii6vuTkheAvKfFf+llH4M9SnES4NSF2hj9NnHga9V08wfhYc
x0W6qu+S8HUdVF+V23yTvUNgz4Q+UoGs4sHSDEsIBFqNvInnpUmtNgcR2L5PAgMB
AAGjUzBRMB0GA1UdDgQWBBTPo8kfze4P9EgxNuyk7+xDGFtAYzAfBgNVHSMEGDAW
gBTPo8kfze4P9EgxNuyk7+xDGFtAYzAPBgNVHRMBAf8EBTADAQH/MA0GCSqGSIb3
DQEBCwUAA4ICAQAKHomtmcGqyiLnhziLe97Mq2+Sul5QgYVwfx/KYOXxv2T8ZmcR
Ae9XFhZT4jsAOUDK1OXx9aZgDGJHJLNEVTe9zWv1ONFfNxEBxQgP7hhmDBWdtj6d
taqEW/Jp06X+08BtnYK9NZsvDg2YRcvOHConeMjwvEL7tQK0m+GVyQfLYg6jnrhx
egH+abucTKxabFcWSE+Vk0uJYMqcbXvB4WNKz9vj4V5Hn7/DN4xIjFko+nREw6Oa
/AUFjNnO/FPjap+d68H1LdzMH3PSs+yjGid+6Zx9FCnt9qZydW13Miqg3nDnODXw
+Z682mQFjVlGPCA5ZOQbyMKY4tNazG2n8qy2famQT3+jF8Lb6a4NGbnpeWnLMkIu
jWLWIkA9MlbdNXuajiPNVyYIK9gdoBzbfaKwoOfSsLxEqlf8rio1GGcEV5Hlz5S2
txwI0xdW9MWeGWoiLbZSbRJH4TIBFFtoBG0LoEJi0C+UPwS8CDngJB4TyrZqEld3
rH87W+Et1t/Nepoc/Eoaux9PFp5VPXP+qwQGmhir/hv7OsgBhrkYuhkjxZ8+1uk7
tUWC/XM0mpLoxsq6vVl3AJaJe1ivdA9xLytsuG4iv02Juc593HXYR8yOpow0Eq2T
U5EyeuFg5RXYwAPi7ykw1PW7zAPL4MlonEVz+QXOSx6eyhimp1VZC11SCg==
-----END CERTIFICATE-----
subject=CN = SnakeOil
issuer=CN = SnakeOil
---
No client certificate CA names sent
Peer signing digest: SHA256
Peer signature type: RSA-PSS
Server Temp Key: X25519, 253 bits
---
SSL handshake has read 2103 bytes and written 373 bytes
Verification error: self-signed certificate
---
New, TLSv1.3, Cipher is TLS_AES_256_GCM_SHA384
Server public key is 4096 bit
Secure Renegotiation IS NOT supported
Compression: NONE
Expansion: NONE
No ALPN negotiated
Early data was not sent
Verify return code: 18 (self-signed certificate)
---
---
Post-Handshake New Session Ticket arrived:
SSL-Session:
    Protocol  : TLSv1.3
    Cipher    : TLS_AES_256_GCM_SHA384
    Session-ID: DE73CFAABF681D83AFF488D32CF71F3D2A298EE42AB06A0D461F3298F4AB2642
    Session-ID-ctx: 
    Resumption PSK: 7BDF798C916AF2FE4996E5BC6F3FE0B6EBF29B5D78A4B695D1EC6805FBA7FE2B3AA16ED7A83F036402585684F4F5C648
    PSK identity: None
    PSK identity hint: None
    SRP username: None
    TLS session ticket lifetime hint: 300 (seconds)
    TLS session ticket:
    0000 - 3d bf b8 cc d8 66 17 4c-f9 cc fb 19 9a 2c 0b 08   =....f.L.....,..
    0010 - 9c 44 46 30 a2 0d dd 2e-3a bb f6 5f bc 36 a1 8e   .DF0....:.._.6..
    0020 - 59 a7 d0 8f 94 a2 bc b5-e2 7e c3 4d ab a7 75 9e   Y........~.M..u.
    0030 - 6a d4 ad 8a 8a 09 27 ba-7b 91 ce 12 5f 4a 71 0f   j.....'.{..._Jq.
    0040 - 65 d9 98 62 69 89 3c 5f-07 0e e5 6b e9 ad 1a d7   e..bi.<_...k....
    0050 - 60 37 c7 27 83 0b e7 b3-08 f8 c3 88 15 29 77 05   `7.'.........)w.
    0060 - c1 5d cb ed 44 2b 76 c8-20 17 ae ea de ea 90 18   .]..D+v. .......
    0070 - 0c d3 d5 48 8e a3 b4 a8-c7 14 57 0c 44 1a 51 91   ...H......W.D.Q.
    0080 - 7a 7e d0 e6 fc c2 41 f2-55 f1 e2 71 e0 86 79 24   z~....A.U..q..y$
    0090 - d1 7a 8e 94 e2 89 ed 43-1a 63 28 33 96 d7 49 6e   .z.....C.c(3..In
    00a0 - fd 9c 7e 32 23 17 06 3e-4c 65 f6 2a e9 5c d8 e4   ..~2#..>Le.*.\..
    00b0 - f8 4e 00 57 cc 63 12 f3-44 7c 3a 4f 82 79 67 b9   .N.W.c..D|:O.yg.
    00c0 - f1 75 6b 54 3c 22 95 1a-16 1f 85 76 9c d1 e2 f2   .ukT<".....v....
    00d0 - d6 b6 d9 70 d9 e4 f8 1c-e6 02 00 61 85 b1 e0 e2   ...p.......a....

    Start Time: 1773249706
    Timeout   : 7200 (sec)
    Verify return code: 18 (self-signed certificate)
    Extended master secret: no
    Max Early Data: 0
---
read R BLOCK
---
Post-Handshake New Session Ticket arrived:
SSL-Session:
    Protocol  : TLSv1.3
    Cipher    : TLS_AES_256_GCM_SHA384
    Session-ID: 78117C66FCD4D3B340B08B6247DAE1F5DD979A140DC6C0FF79C167AE9BAB8A60
    Session-ID-ctx: 
    Resumption PSK: 83760001219CD4F5CF2690FC551A1B92A2BBEB031FA45655AB066A2032257F1D09AB55C9E45C2FD9D440313DCECAF791
    PSK identity: None
    PSK identity hint: None
    SRP username: None
    TLS session ticket lifetime hint: 300 (seconds)
    TLS session ticket:
    0000 - 3d bf b8 cc d8 66 17 4c-f9 cc fb 19 9a 2c 0b 08   =....f.L.....,..
    0010 - 6c 33 5a e4 0e 28 c1 3a-e0 83 38 cf bc 0d 3d b1   l3Z..(.:..8...=.
    0020 - 54 24 b7 bf f2 f9 31 f4-19 ed 4a 9e b1 2b 55 31   T$....1...J..+U1
    0030 - 83 3c fa 6d 8b de 6d fc-52 77 ed d4 78 93 d8 1f   .<.m..m.Rw..x...
    0040 - 7f 71 7f 8f eb 55 c1 50-9d ee c8 a9 e9 7d 90 c9   .q...U.P.....}..
    0050 - b5 e1 e8 47 5b d3 6c 2c-f2 c3 f6 1d ed 91 d3 a6   ...G[.l,........
    0060 - d5 1f 84 ed b9 ff 88 f3-16 93 df 67 cc b9 a5 18   ...........g....
    0070 - 74 7c 5a 77 89 c9 50 ab-f6 59 50 7e 19 66 7d 2d   t|Zw..P..YP~.f}-
    0080 - 40 78 98 1a e3 ad e1 c5-dc ab 9c 8d 34 1d d3 d9   @x..........4...
    0090 - 91 8c ca 71 2d de f2 e5-fb 7b e4 88 88 23 6e 3f   ...q-....{...#n?
    00a0 - 79 01 12 62 94 95 64 3a-18 06 de a7 c3 2e 1a 64   y..b..d:.......d
    00b0 - 5b 86 f2 92 10 56 b3 37-b6 08 0f ba 57 39 90 fc   [....V.7....W9..
    00c0 - f6 49 1b ba bb 08 53 64-00 9a 58 47 ed 79 23 13   .I....Sd..XG.y#.
    00d0 - 32 0a c6 b2 15 9c 72 12-76 ba df 2b b5 92 1d b7   2.....r.v..+....

    Start Time: 1773249706
    Timeout   : 7200 (sec)
    Verify return code: 18 (self-signed certificate)
    Extended master secret: no
    Max Early Data: 0
---
read R BLOCK


```

