// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'finalBossSpawner': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest/block/entity/spawner/FinalBossSpawnerBlockEntity',
                'methodName': 'spawnMyBoss',
                'methodDesc': '(Lnet/minecraft/world/level/ServerLevelAccessor;)Z'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.ICONST_0),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            'net/minecraft/world/level/block/entity/BlockEntity',
                            ASM.mapMethod('m_58899_'), // getBlockPos
                            '()Lnet/minecraft/core/BlockPos;'
                            ),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'io/github/jodlodi/twilighttweaks/ASMHooks',
                            'finalSpawner',
                            '(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/ServerLevelAccessor;)Z',
                            false
                            ),
                        new InsnNode(Opcodes.IRETURN)
                        )
                    );
                return methodNode;
            }
        }
    }
}
