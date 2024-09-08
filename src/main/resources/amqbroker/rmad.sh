$ cat rmad.sh
#!/bin/bash

# Set broker connection details
BROKER_URL=tcp://localhost:61616
BROKER_USER=artemis
BROKER_PASSWORD=artemis
ADDR_FILE="addr_to_delete.txt"

# Check if the file with queue names exists
if [[ ! -f "$ADDR_FILE" ]]; then
  echo "File $ADDR_FILE not found!"
  exit 1
fi

# Loop through each line (add name) in the file
while IFS= read -r addr; do
  # Trim any whitespace around the queue name
  addr=$(echo "$addr" | xargs)

  # Skip empty lines
  if [[ -z "$addr" ]]; then
    continue
  fi

  echo "Deleting address: $addr"

  # Delete the addr
  ./artemis address delete --name "$addr" --url $BROKER_URL --user $BROKER_USER --password $BROKER_PASSWORD

done < "$ADDR_FILE"
