# Start the RabbitMQ server:

Otherwise none of the rest will work.

    docker compose up --detach rabbitmq

# Send a message

    sbt "send/run hello"

# Receive the message

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
