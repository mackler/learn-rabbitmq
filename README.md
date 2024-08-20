# Start the RabbitMQ server:

Otherwise none of the rest will work.

    docker compose up --detach rabbitmq

# Send a message

From the sbt prompt.

    send/run """{"name": "joe", "priority": 3, "what": "start"}"""

# Receive the message

Note: the receiver must know the name of the host to connect to, which
is different depending on whether or not it’s running in a container.
Without a container the hostname is "localhost".

    sbt receiver/run

# Packaging the receiver in Docker

First make the image:

    sbt receiver/docker:publishLocal

or, from within `sbt`

    Docker/publishLocal

This command requires authentication, so either run as root or as a
user in the `docker` group.

Then,

    docker compose up receiver

Now the receiver is running, waiting for messages.  Send a message as above.
Hostname will be "rabbitmq"
