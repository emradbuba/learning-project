#!/bin/bash

# Set broker connection details
BROKER_URL=tcp://localhost:61616
BROKER_USER=artemis
BROKER_PASSWORD=artemis
QUEUE_FILE="queues_to_delete.txt"

# Check if the file with queue names exists
if [[ ! -f "$QUEUE_FILE" ]]; then
  echo "File $QUEUE_FILE not found!"
  exit 1
fi

# Loop through each line (queue name) in the file
while IFS= read -r queue; do
  # Trim any whitespace around the queue name
  queue=$(echo "$queue" | xargs)

  # Skip empty lines
  if [[ -z "$queue" ]]; then
    continue
  fi

  echo "Deleting queue: $queue"

  # Delete the queue
  ./artemis queue delete --name "$queue" --url $BROKER_URL --user $BROKER_USER --password $BROKER_PASSWORD

done < "$QUEUE_FILE"
