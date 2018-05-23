#python generate_process_vp.py numero_procesos max_cpu

import random
import sys

numero_proc = sys.argv[1]
tiempo_max = sys.argv[2]

exec_time = 1

f = file("process.xml", "w")

f.write('<?xml version="1.0" encoding="UTF-8"?>\n')

f.write("<ProcessInfo>\n")

for i in range( int(numero_proc) ):
    f.write('\t<Process Id="' + str(i) + '">\n')
    #f.write("\t\t<Id>"+ str(i)  +"</Id>\n")
    f.write("\t\t<ExecutionTime>"+ str(random.randint(0, int(tiempo_max)))  +"</ExecutionTime>\n")

    exec_time += 1

    # mysize = random.randint( 10, int(data_size))
    # f.write("\t\t<DataSize>"+ str(mysize)  +"</DataSize>\n")
    # f.write("\t\t<Instructions>\n")

    # for j in range( random.randint(10, int(data_size) / int(instxpage) ) ):
    #     f.write("\t\t\t<Operation>\n")
    #     f.write("\t\t\t\t<AccessedAddress>"+ str(random.randint( 0, mysize))  +"</AccessedAddress>\n")
    #     f.write("\t\t\t\t<InstructionType>"+ random.choice(["READ", "WRITE"])  +"</InstructionType>\n")
    #     f.write("\t\t\t</Operation>\n")
        
    # f.write("\t\t</Instructions>\n")
    f.write("\t</Process>\n")

f.write("</ProcessInfo>\n")
