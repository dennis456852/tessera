
def jarfile = properties['jarfile']

def publicKeys = properties['publicKeys']

def privateKeys = properties['privateKeys']

log.info "$jarfile"

def processDesc = "java -jar $jarfile -publicKeys $publicKeys -privateKeys $privateKeys"

log.info "$processDesc"

def countdownLatch = new java.util.concurrent.CountDownLatch(1)

def process = "$processDesc".execute();
def t = new Thread({
    process.waitFor()
    countdownLatch.countDown()
})
t.start()
countdownLatch.await(1,java.util.concurrent.TimeUnit.MINUTES)


