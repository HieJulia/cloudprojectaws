#!/usr/bin/env bash

echo "Ansible is started"

ANSIBLE_HOST_KEY_CHECKING=false ansible-playbook -i ../terraform/hosts --private-key ../terraform/key/beerstore_key project-playbook.yml