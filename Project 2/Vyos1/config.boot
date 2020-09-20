high-availability {
    vrrp {
        group LAN15 {
            interface eth1
            priority 100
            virtual-address 10.0.5.1/24
            vrid 11
        }
        group OPT15 {
            interface eth2
            priority 100
            virtual-address 10.0.6.1/24
            vrid 12
        }
        group WAN15 {
            interface eth0
            priority 100
            virtual-address 10.0.17.85/24
            vrid 10
        }
    }
}
interfaces {
    ethernet eth0 {
        address 10.0.17.25/24
        description WAN
        hw-id 00:50:56:b3:24:a8
    }
    ethernet eth1 {
        address 10.0.5.2/24
        description LAN
        hw-id 00:50:56:b3:36:b2
    }
    ethernet eth2 {
        address 10.0.6.2/24
        description OPT
        hw-id 00:50:56:b3:6b:fc
    }
    loopback lo {
    }
}
nat {
    destination {
        rule 10 {
            description "Port Fwd 80"
            destination {
                port 80
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 10.0.6.10
            }
        }
        rule 15 {
            description "Port 22"
            destination {
                port 22
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 10.0.5.100
            }
        }
    }
    source {
        rule 10 {
            description "NAT FROM LAN TO WAN"
            outbound-interface eth0
            source {
                address 10.0.5.0/24
            }
            translation {
                address masquerade
            }
        }
        rule 15 {
            description "NAT from OPT to WAN"
            outbound-interface eth0
            source {
                address 10.0.6.0/24
            }
            translation {
                address masquerade
            }
        }
    }
}
protocols {
    static {
        route 0.0.0.0/0 {
            next-hop 10.0.17.2 {
            }
        }
    }
}
service {
    dns {
        forwarding {
            allow-from 10.0.5.0/24
            allow-from 10.0.6.0/24
            listen-address 10.0.5.2
            listen-address 10.0.6.2
        }
    }
    ssh {
        listen-address 10.0.17.25
        port 22
    }
}
system {
    config-management {
        commit-revisions 100
    }
    console {
        device ttyS0 {
            speed 115200
        }
    }
    host-name vyos1-ryan
    login {
        user vyos {
            authentication {
                encrypted-password $6$IkGxPeNjcIz8rUED$4ls6jTtzh4u7SKY8rwVYMpFoUOWlMubiW2fTxFR0WGzlI1CoGGH4auzhESaAUuVT1.UtdNvimD7KUaNwqkXiq1
                plaintext-password ""
            }
        }
    }
    name-server 10.0.17.2
    ntp {
        server 0.pool.ntp.org {
        }
        server 1.pool.ntp.org {
        }
        server 2.pool.ntp.org {
        }
    }
    syslog {
        global {
            facility all {
                level info
            }
            facility protocols {
                level debug
            }
        }
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "broadcast-relay@1:cluster@1:config-management@1:conntrack@1:conntrack-sync@1:dhcp-relay@2:dhcp-server@5:dhcpv6-server@1:dns-forwarding@3:firewall@5:https@2:interfaces@11:ipoe-server@1:ipsec@5:l2tp@3:lldp@1:mdns@1:nat@5:ntp@1:pppoe-server@3:pptp@2:qos@1:quagga@6:salt@1:snmp@1:ssh@1:sstp@2:system@18:vrrp@2:vyos-accel-ppp@2:wanloadbalance@3:webgui@1:webproxy@2:zone-policy@1"
// Release version: 1.3-rolling-202007010117
