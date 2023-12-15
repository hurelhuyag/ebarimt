#!/usr/bin/env bash

# this script install ebarimt's PosAPI linux service security hardened way

set -e

[ -x unzip ] && echo "Command 'unzip' not found" >&2 && exit 1
[ -x ar ] && echo "Command 'ar' not found" >&2 && exit 1
[ -x tar ] && echo "Command 'tar' not found" >&2 && exit 1
[ -x xz ] && echo "Command 'xz' not found" >&2 && exit 1


sudo systemctl stop posapi >/dev/null || true
sudo systemctl stop PosAPI >/dev/null || true
sudo systemctl disable PosAPI >/dev/null || true
sudo useradd ebarimt >/dev/null || true

FILE="ST_PosService_3.0.9.zip";

if [ "$1" == "--prod" ]
then
  FILE="PosService_3.0.9.zip"
fi

curl -o $FILE https://developer.itc.gov.mn/assets/files/$FILE
unzip $FILE
ar --output ./Package/linux/ -vx ./Package/linux/PosAPI.deb
tar --xz -xf ./Package/linux/data.tar.xz -C ./Package/linux/

sudo mkdir -p /opt/posapi
sudo mkdir -p /etc/posapi
sudo mkdir -p /var/log/ebarimt/

sudo cp -fd ./Package/linux/usr/lib/* /usr/lib/
sudo cp -f ./Package/linux/opt/posapi/PosService /opt/posapi/PosService
sudo cp -f ./Package/linux/etc/posapi/posapi.ini /etc/posapi/posapi.ini
sudo touch /var/log/ebarimt/posapi.log
sudo chown ebarimt:admin -R /var/log/ebarimt

sudo chown ebarimt:ebarimt -R /opt/posapi

SERVICE=$(cat <<-END
[Unit]
Description = Нэмэгдсэн өртгийн албан татварын систем
Requires = network.target

[Service]
User = ebarimt
Group = ebarimt
ProtectSystem = full
ProtectHome = yes
LogsDirectory = ebarimt
WorkingDirectory = /opt/posapi
ReadWritePaths = /opt/posapi
ExecStart = /opt/posapi/PosService
Restart = always
RemainAfterExit = yes
StandardOutput = append:/var/log/ebarimt/posapi.log
StandardError  = append:/var/log/ebarimt/posapi.log

[Install]
WantedBy = multi-user.target

END
)

echo "$SERVICE" | sudo tee /etc/systemd/system/posapi.service >/dev/null

sudo systemctl daemon-reload
sudo systemctl enable posapi
sudo systemctl start posapi

rm -r ./Package