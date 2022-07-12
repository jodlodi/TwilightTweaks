// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'bossSpawner': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest/block/entity/spawner/BossSpawnerBlockEntity',
                'methodName': 'tick',
                'methodDesc': '(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Ltwilightforest/block/entity/spawner/BossSpawnerBlockEntity;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insert(
                    ASM.findFirstInstruction(methodNode, Opcodes.PUTFIELD),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new VarInsnNode(Opcodes.ALOAD, 2),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'io/github/jodlodi/twilighttweaks/ASMHooks',
                            'bossSpawner',
                            '(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        }
    }
}
