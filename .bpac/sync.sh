#!/bin/sh

set -o errexit

[ -d "$$HOME/.ssh" ] || mkdir -p "$$HOME/.ssh"

cp ./.bpac/ssh_config "$HOME/.ssh/config"

apk add rsync && apk add bash && bash ./.bpac/gitsync.sh
