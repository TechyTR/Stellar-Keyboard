import logging
import os

from stellar_config import (
    CACHE_DIRECTORY
)


def create_logger():
    os.makedirs(
        CACHE_DIRECTORY,
        exist_ok=True
    )

    logger = logging.getLogger(
        "StellarKeyboard"
    )

    if logger.handlers:
        return logger

    logger.setLevel(
        logging.INFO
    )

    log_file = os.path.join(
        CACHE_DIRECTORY,
        "stellar-keyboard.log"
    )

    handler = logging.FileHandler(
        log_file,
        encoding="utf-8"
    )

    formatter = logging.Formatter(
        "%(asctime)s "
        "[%(levelname)s] "
        "%(message)s"
    )

    handler.setFormatter(
        formatter
    )

    logger.addHandler(
        handler
    )

    return logger


logger = create_logger()
